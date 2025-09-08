
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
        while (rs.next()) out.add(new Roles(rs.getInt(1), rs.getString(2)));
        return out;
    }
}
    
    public List<Roles> listarTodos() {
    // Si tienes soft delete: "WHERE activo = TRUE"
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


public boolean reemplazarRolesUsuario(int idUsuario, List<Integer> rolesIds) {
    String del = "DELETE FROM public.usuario_roles WHERE usuario_id = ?";
    String ins = "INSERT INTO public.usuario_roles (usuario_id, rol_id) VALUES (?, ?)";
    try (var con = ConexionDB.conectar()) {
        con.setAutoCommit(false);

        try (var ps = con.prepareStatement(del)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
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

        con.commit();
        con.setAutoCommit(true);
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
