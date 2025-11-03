
package com.mycompany.controlasistencia.dao;

import com.mycompany.controlasistencia.config.ConexionDB;
import com.mycompany.controlasistencia.modelo.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class RolesDAO {
    public List<Roles> listarRoles() throws SQLException {
    String sql = "SELECT id_rol, nombre FROM roles ORDER BY id_rol";
    try (Connection c = ConexionDB.conectar();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        List<Roles> out = new ArrayList<>();
        //Recorre los resulados y crea el objeto de roles
        while (rs.next()) out.add(new Roles(rs.getInt(1), rs.getString(2)));
        return out;
    }
}
    
    public List<Roles> listarTodos() {
    String sql = "SELECT id_rol, nombre FROM roles ORDER BY nombre";
    List<Roles> out = new ArrayList<>();
    try (var con = ConexionDB.conectar();
         var ps = con.prepareStatement(sql);
         var rs = ps.executeQuery()) {
        while (rs.next()) {
            out.add(new Roles(rs.getInt("id_rol"), rs.getString("nombre")));
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return out;
}

// Devolver los roles asignados a un usuario especifico 
public List<Roles> listarPorUsuario(int idUsuario) {
    String sql = """
      SELECT r.id_rol, r.nombre
      FROM public.usuario_roles ur
      JOIN public.roles r ON r.id_rol = ur.rol_id
      WHERE ur.usuario_id = ?
      ORDER BY r.nombre
    """;
    List<Roles> out = new ArrayList<>();
    try (var con = ConexionDB.conectar();
         var ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        try (var rs = ps.executeQuery()) {
            while (rs.next()) out.add(new Roles(rs.getInt("id_rol"), rs.getString("nombre")));
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return out;
}

    // Primero elimina los roles antiguos del usuario y luego inserta los nuevo 
    public boolean reemplazarRolesUsuario(int idUsuario, List<Integer> rolesIds) {
        String del = "DELETE FROM public.usuario_roles WHERE usuario_id = ?";
        String ins = "INSERT INTO public.usuario_roles (usuario_id, rol_id) VALUES (?, ?)";
        try (var con = ConexionDB.conectar()) {
            con.setAutoCommit(false); 

            try (var ps = con.prepareStatement(del)) {
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
            }
            //Inserta los roles nuevo
            if (rolesIds != null) {
                try (var ps = con.prepareStatement(ins)) {
                    for (Integer idRol : rolesIds) {
                        ps.setInt(1, idUsuario);
                        ps.setInt(2, idRol);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }

            con.commit(); //Confirma
            con.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verifica si el usuario tiene un rol especifico, retorna true si el usuario tiene rol
    public boolean usuarioTieneRolNombre(int idUsuario, String nombreRol) {
    String sql = """
        SELECT 1
        FROM public.usuario_roles ur
        JOIN public.roles r ON r.id_rol = ur.rol_id
        WHERE ur.usuario_id = ? AND UPPER(r.nombre) = UPPER(?)
        LIMIT 1
    """;
    try (Connection con = ConexionDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ps.setString(2, nombreRol);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); //si hay resultado es porque el usuario tiene ese rol
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // ¿El usuario tiene el rol "ADMIN" o "ADMINISTRADOR"?
    public boolean esAdmin(int idUsuario) {
    String sql = """
        SELECT 1
        FROM public.usuario_roles ur
        JOIN public.roles r ON r.id_rol = ur.rol_id
        WHERE ur.usuario_id = ?
          AND UPPER(r.nombre) IN ('ADMIN')
        LIMIT 1
    """;
    try (Connection con = ConexionDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    //Variante que ademas registra la accion en el historial
    public boolean reemplazarRolesUsuarioConLog(int actorId, int idUsuario, java.util.List<Integer> rolesIds) {
    boolean ok = reemplazarRolesUsuario(idUsuario, rolesIds);
    if (ok) {
        new HistorialDAO().log(
            actorId,                     //Quien realizó la accion
            "USUARIO_ROLES_REEMPLAZAR",  //Tipo de accion
            "USUARIO",                   //Tipo de objeto afectado
            idUsuario,                   // Id del usuario afectado
            "roles=" + String.valueOf(rolesIds) //Detalle de los nuevos roles 
         );
    }
    return ok;
}
    


    
}
