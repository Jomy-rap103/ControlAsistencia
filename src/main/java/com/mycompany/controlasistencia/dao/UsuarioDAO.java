package com.mycompany.controlasistencia.dao;

import com.mycompany.controlasistencia.config.ConexionDB;
import com.mycompany.controlasistencia.config.Seguridad;
import com.mycompany.controlasistencia.modelo.Usuario;

import java.sql.*;

public class UsuarioDAO {

    // Valida por "nombre" y que esté activo; compara BCrypt
    public Usuario iniciarSesion(String nombre, String contrasenaIngresada) {
        String sql = """
            SELECT id_usuario, nombre, apellido, contrasena, activo
            FROM usuarios
            WHERE lower(nombre) = lower(?) AND activo = TRUE
            LIMIT 1
        """;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("contrasena");
                    if (hash == null || hash.isBlank()) return null; 
                    
                    if (Seguridad.verificar(contrasenaIngresada, hash)) {
                        return new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                /* contra */ null,
                                rs.getBoolean("activo")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
    }
    
    
    public Usuario crearCuenta(String nombre, String apellido, String contrasenaOpcional) {
        String sql = """
            INSERT INTO usuarios (nombre, apellido, contrasena, activo)
            VALUES (?, ?, ?, TRUE)
            RETURNING id_usuario, nombre, apellido, activo
        """;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre.trim());
            ps.setString(2, apellido.trim());

            String hash = (contrasenaOpcional == null || contrasenaOpcional.isBlank())
                    ? null
                    : Seguridad.hash(contrasenaOpcional);

            if (hash == null) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, hash);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            null,
                            rs.getBoolean("activo")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public java.util.List<Usuario> listarTodos() {
    String sql = "SELECT id_usuario, nombre, apellido, activo FROM usuarios ORDER BY id_usuario";
    java.util.List<Usuario> lista = new java.util.ArrayList<>();

    try (Connection con = ConexionDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            lista.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    null,
                    rs.getBoolean("activo")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
    
    public boolean actualizarUsuario(int idUsuario, String nombre, String apellido, String nuevaContrasenaNullable, boolean activo) {
    String sql = """
        UPDATE usuarios
        SET nombre = ?, 
            apellido = ?, 
            contrasena = COALESCE(?, contrasena),
            activo = ?
        WHERE id_usuario = ?
    """;

    try (Connection con = ConexionDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nombre.trim());
        ps.setString(2, apellido.trim());

        if (nuevaContrasenaNullable == null || nuevaContrasenaNullable.isBlank()) {
            ps.setNull(3, Types.VARCHAR); //AQUI SE MANTIENE LA CONTRASEÑA ACTUAL
        } else {
            String hash = Seguridad.hash(nuevaContrasenaNullable);
            ps.setString(3, hash);
        }

        ps.setBoolean(4, activo);
        ps.setInt(5, idUsuario);

        return ps.executeUpdate() == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
