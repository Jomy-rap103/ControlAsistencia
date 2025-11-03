package com.mycompany.controlasistencia.dao;

import com.mycompany.controlasistencia.config.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialDAO {
    // Etiquetas de objeto en la tabla usadas para el registro
    public static final String OBJ_USUARIO = "USUARIO";
    public static final String OBJ_HORARIO = "HORARIO";
    public static final String OBJ_ROL     = "ROL";
    public static final String OBJ_ASIST   = "ASISTENCIA";
    public static final String OBJ_SISTEMA = "SISTEMA";     

// Infiere el tipo de objeto a partir del texto de la accion, asi llamo al log sin especificar el objeto    
private static String inferirObjeto(String accion) {
    if (accion == null) return OBJ_SISTEMA;
    String a = accion.toUpperCase();

    if (a.contains("HORARIO"))                  return OBJ_HORARIO;    
    if (a.contains("USUARIO"))                  return OBJ_USUARIO;   
    if (a.contains("ROL"))                      return OBJ_ROL;        
    if (a.contains("ASIST") || a.contains("ENTRADA") || a.contains("SALIDA"))
                                               return OBJ_ASIST;       
    return OBJ_SISTEMA;
}
    //Inferir objeto desde la accion     
    public boolean log(Integer actorId, String accion, Integer objetoId, String detalles) {
    String objeto = inferirObjeto(accion); 
    return log(actorId, accion, objeto, objetoId, detalles);
}

    //Insert en el historial, actorID es quien ejecuta, accion es la etiqueta, objeto la categoria, objetoID es el id de la persona afectada y detalles que es texto
    public boolean log(Integer actorId, String accion, String objeto, Integer objetoId, String detalles) {
    String sql = """
        INSERT INTO historial (actor_id, accion, objeto, objeto_id, detalles, fecha_hora)
        VALUES (?, ?, ?, ?, ?, now())
    """;
    try (var con = ConexionDB.conectar();
         var ps  = con.prepareStatement(sql)) {
        ps.setObject(1, actorId);
        ps.setString(2, accion);
        ps.setString(3, objeto);
        ps.setObject(4, objetoId);
        ps.setString(5, detalles);
        return ps.executeUpdate() == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



    
    public static record Item(
        long id, java.time.LocalDateTime fechaHora,
        Integer actorId, String actorNombre,
        String accion, String objeto, Integer objetoId,
        String objetivoNombre, String detalles
    ) {}
    
    //Esto es para listar los ultimos LIMIT eventos historial para que los mas recientes sean primero
    public List<Item> listarUltimos(int limit) {
    String sql = """
        SELECT h.id, h.fecha_hora,
               h.actor_id,
               COALESCE(a.nombre,'') || ' ' || COALESCE(a.apellido,'') AS actor_nombre,
               h.accion, h.objeto, h.objeto_id,
               COALESCE(u.nombre,'') || ' ' || COALESCE(u.apellido,'') AS objetivo_nombre,
               h.detalles
        FROM historial h
        LEFT JOIN usuarios a ON a.id_usuario = h.actor_id
        LEFT JOIN usuarios u ON u.id_usuario = h.objeto_id
        ORDER BY h.fecha_hora DESC, h.id DESC
        LIMIT ?
    """;

    List<Item> out = new ArrayList<>();
    try (Connection con = ConexionDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Math.max(1, limit)); //Evito el LIMIT 0
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Item(
                    rs.getLong("id"),
                    rs.getTimestamp("fecha_hora").toLocalDateTime(), //paso de timestamp a LocalDateTime
                    (Integer) rs.getObject("actor_id"),
                    rs.getString("actor_nombre"),
                    rs.getString("accion"),
                    rs.getString("objeto"),
                    (Integer) rs.getObject("objeto_id"),
                    rs.getString("objetivo_nombre"),
                    rs.getString("detalles")
                ));
            }
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return out;
}
    //Logear cambios de horario
    public boolean logEditarHorario(Integer actorId, int usuarioId, String detalle) {
        return log(actorId, "EDITAR_HORARIO", usuarioId, detalle);
    }

    //Listar los eventos del horario, editar,crear y eliminar
    public List<Item> listarCambiosHorario(int limit) {
        String sql = """
            SELECT h.id, h.fecha_hora,
                   h.actor_id, (a.nombre || ' ' || a.apellido) AS actor_nombre,
                   h.accion, h.objeto, h.objeto_id,
                   (u.nombre || ' ' || u.apellido) AS objetivo_nombre,
                   h.detalles
            FROM historial h
            LEFT JOIN usuarios a ON a.id_usuario = h.actor_id
            LEFT JOIN usuarios u ON u.id_usuario = h.objeto_id
            WHERE h.accion IN ('CREAR_HORARIO','EDITAR_HORARIO','ELIMINAR_HORARIO')
            ORDER BY h.fecha_hora DESC
            LIMIT ?
        """;
        List<Item> out = new ArrayList<>();
        try (var con = ConexionDB.conectar();
             var ps  = con.prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limit));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Item(
                        rs.getLong("id"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        (Integer) rs.getObject("actor_id"),
                        rs.getString("actor_nombre"),
                        rs.getString("accion"),
                        rs.getString("objeto"),
                        (Integer) rs.getObject("objeto_id"),
                        rs.getString("objetivo_nombre"),
                        rs.getString("detalles")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    //Elimina el horario por id_horario pero deja constancia en el historial
    public boolean eliminarHorario(int idHorario, int actorId) throws SQLException {
        final String sql = """
            WITH del AS (
              DELETE FROM public.horarios
              WHERE id_horario = ?
              RETURNING usuario_id, dia_semana, hora_entrada, hora_salida
            )
            INSERT INTO historial (actor_id, accion, objeto, objeto_id, detalles)
            SELECT ?::int,
                   'ELIMINAR_HORARIO',
                   'HORARIO',
                   del.usuario_id,
                   json_build_object(
                     'dia_semana', del.dia_semana,
                     'hora_entrada', del.hora_entrada,
                     'hora_salida', del.hora_salida
                   )::text
            FROM del;
        """;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setInt(1, idHorario); //id del registro a borrar
            ps.setInt(2, actorId);  //Quien borra

            int rows = ps.executeUpdate(); 
            con.commit();
            return rows == 1;
        }
    }
}
