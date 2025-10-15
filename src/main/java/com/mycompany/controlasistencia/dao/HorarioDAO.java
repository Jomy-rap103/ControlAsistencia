package com.mycompany.controlasistencia.dao;

import com.mycompany.controlasistencia.config.ConexionDB;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    
    //Buscar el id por nombre y apellido, devuelve null en caso de error o si no existe
    public Integer buscarUsuarioId(String nombre, String apellido) {
        String sql = """
            SELECT id_usuario FROM public.usuarios
            WHERE lower(nombre)=lower(?) AND lower(apellido)=lower(?)
            LIMIT 1
        """;
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre.trim());
            ps.setString(2, apellido.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    //Listar valores de ENUM con los dias de la semana, asi aseguro que UI y base datos compartan los mismos orden
    public List<String> listarDiasSemana() {
        String sql = "SELECT unnest(enum_range(NULL::public.dia_semana))::text";
        List<String> out = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    // Listar horarios del usuario, 
    public List<Object[]> listarHorarios(int usuarioId) {
        String sql = """
            SELECT dia_semana::text,
                   to_char(hora_entrada,'HH24:MI'),
                   to_char(hora_salida,'HH24:MI')
            FROM public.horarios
            WHERE usuario_id = ?
            ORDER BY dia_semana
        """;
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                // Creo un bucle para crear filas que poblen el JTable 
                while (rs.next()) rows.add(new Object[]{ rs.getString(1), rs.getString(2), rs.getString(3) });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rows;
    }

    // Insertar o actualizar el horario para usuario_id en dia_semana, true si afeco una fila al menos
    public boolean upsertHorario(int usuarioId, String diaSemana, LocalTime entrada, LocalTime salida) {
        String sql = """
            INSERT INTO public.horarios (usuario_id, dia_semana, hora_entrada, hora_salida)
            VALUES (?, ?::public.dia_semana, ?, ?)
            ON CONFLICT (usuario_id, dia_semana)
            DO UPDATE SET hora_entrada = EXCLUDED.hora_entrada,
                          hora_salida  = EXCLUDED.hora_salida
        """;
        try (Connection c = ConexionDB.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, diaSemana); //castea enum en sql
            ps.setObject(3, entrada); //LocalTime
            ps.setObject(4, salida);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    //eliminar horario xd
    public boolean borrarHorarioDia(int usuarioId, String diaSemana) {
        String sql = "DELETE FROM public.horarios WHERE usuario_id=? AND dia_semana=?::public.dia_semana";
        try (Connection c = ConexionDB.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, diaSemana);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    
    //Obtener el horario de entrada y salida para un usuario en un dia
    public LocalTime[] obtenerHorario (int usuarioId, String diaSemana) {
    
        String sql = """
                     SELECT hora_entrada, hora_salida 
                     FROM public.horarios
                     WHERE usuario_id = ? AND dia_semana = ?:: public.dia_semana
                     """;
        try (Connection conn = ConexionDB.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, diaSemana);
            
            try (ResultSet rs = ps.executeQuery() ) {
                if (rs.next()) {
                    //Consigo el tiempo de las consultas 1 y 2
                    Time tE = rs.getTime(1);
                    Time tS = rs.getTime(2);
                    
                    if (tE != null && tS != null) {
                        return new LocalTime[]{ tE.toLocalTime(), tS.toLocalTime() };
                    }
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return null; //no existe o faltan horas 
    }
    
    // Upsert con auditoria, evitar log redundante cuando no hay cambios en entrada o salida
    public boolean upsertHorarioLog (Integer actorId, int usuarioId, String diaSemana,
            LocalTime entrada, LocalTime salida) {
        
        //Leer valores actuales para comparar y evitar logs innecesarios
        LocalTime[] antes = obtenerHorario(usuarioId, diaSemana);
        
        //Si no hay cambios
        if (antes != null && entrada.equals(antes[0]) && salida.equals(antes[1])) {
        return true;
        }
        
        boolean ok = upsertHorario(usuarioId, diaSemana, entrada, salida);
        
        if (ok) {
            //Texto para el historial
            String det = "Dia: " + diaSemana + " | antes: " + (antes == null ? "(sin horario)" : (antes [0] + "-" + antes[1]))
                    + " | Despues: " + entrada + "-" + salida;
            
            //Se registra en historial
            new HistorialDAO().log(actorId, "EDITAR_HORARIO", usuarioId, det);
        
        }
        return ok;
    
    }
    
    
} //No borrar este
