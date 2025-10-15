package com.mycompany.controlasistencia.dao;

import com.mycompany.controlasistencia.config.ConexionDB;
import com.mycompany.controlasistencia.config.Seguridad;
import com.mycompany.controlasistencia.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // limpiar el rut para quitar puntos, guion y espacios, lo deja todo en mayusculas
    private static String normalizaRut(String rut) {
        if (rut == null) return null;
        return rut.replaceAll("[.\\s-]", "").toUpperCase();
    }

    // Validar nombre y comparar con el bcrypt (contraseña hasheada)
    public Usuario iniciarSesion(String nombre, String apellido, String contrasenaIngresada) {
        String sql = """
            SELECT id_usuario, nombre, apellido, contrasena, activo, rut
            FROM usuarios
            WHERE activo = true
              AND lower(trim(nombre)) = lower(trim(?))
              AND lower(trim(apellido)) = lower(trim(?))
            LIMIT 1
        """;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("contrasena");
                    if (hash == null || hash.isBlank()) return null;
                    // Verifica la contraseña ingresada contra el hash 
                    if (Seguridad.verificar(contrasenaIngresada, hash)) {
                        return new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                /* contra */ null,
                                rs.getBoolean("activo"),
                                rs.getString("rut")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Crear cuenta con RUT (sin guion) y contraseña opcional
    public Usuario crearCuenta(String nombre, String apellido, String rut, String contrasenaOpcional) {
        String sql = """
            INSERT INTO usuarios (nombre, apellido, rut, contrasena, activo)
            VALUES (?, ?, ?, ?, TRUE)
            RETURNING id_usuario, nombre, apellido, rut, activo
        """;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String rutNorm = normalizaRut(rut);
            String hash = (contrasenaOpcional == null || contrasenaOpcional.isBlank())
                    ? null
                    : Seguridad.hash(contrasenaOpcional);

            // Orden coincide EXACTO con el SQL
            ps.setString(1, nombre.trim());
            ps.setString(2, apellido.trim());
            ps.setString(3, rutNorm);
            if (hash == null) {
                ps.setNull(4, Types.VARCHAR);
            } else {
                ps.setString(4, hash);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            null,
                            rs.getBoolean("activo"),
                            rs.getString("rut")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Devuelve una lista con el usuarios registrados (activos e inactivos)
    public List<Usuario> listarTodos() {
        String sql = "SELECT id_usuario, nombre, apellido, activo, rut FROM usuarios ORDER BY id_usuario";
        List<Usuario> lista = new ArrayList<>();

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        null,
                        rs.getBoolean("activo"),
                        rs.getString("rut")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //Actualiza los datos del usuario y si no ingresa una nueva contraseña se mantiene la actual
    public boolean actualizarUsuario(int idUsuario, String nombre, String apellido, String rut,
                                     String nuevaContrasenaNullable, boolean activo) {
        String sql = """
            UPDATE usuarios
            SET nombre = ?,
                apellido = ?,
                contrasena = COALESCE(?, contrasena),
                activo = ?,
                rut = ?
            WHERE id_usuario = ?
        """;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String hash = (nuevaContrasenaNullable == null || nuevaContrasenaNullable.isBlank())
                    ? null
                    : Seguridad.hash(nuevaContrasenaNullable);

            // Orden EXACTO con el SQL
            ps.setString(1, nombre.trim());
            ps.setString(2, apellido.trim());
            if (hash == null) {
                ps.setNull(3, Types.VARCHAR); // COALESCE(null, contrasena) mantiene la actual
            } else {
                ps.setString(3, hash);
            }
            ps.setBoolean(4, activo);
            ps.setString(5, normalizaRut(rut));
            ps.setInt(6, idUsuario);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar por nombre y apellido (incluye rut en el objeto)
    public Usuario buscarPorNombreApellido(String nombre, String apellido) {
        String sql = """
            SELECT id_usuario, nombre, apellido, activo, rut
            FROM usuarios
            WHERE activo = TRUE
              AND lower(trim(nombre)) = lower(trim(?))
              AND lower(trim(apellido)) = lower(trim(?))
            LIMIT 1
        """;
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            null,
                            rs.getBoolean("activo"),
                            rs.getString("rut")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtiene un usuario por el id
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT id_usuario, nombre, apellido, activo, rut FROM usuarios WHERE id_usuario = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            null,
                            rs.getBoolean("activo"),
                            rs.getString("rut")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Crea una cuenta y registra la accion en el historial
    public Usuario crearCuentaConLog(int actorId, String nombre, String apellido, String rut, String contrasenaOpcional) {
        Usuario u = crearCuenta(nombre, apellido, rut, contrasenaOpcional);
        if (u != null) {
            new HistorialDAO().log(
                actorId,
                "CREAR_USUARIO",
                "USUARIO",
                u.getId_Usuario(),
                "Creado: " + u.getNombre() + " " + u.getApellido() + " (RUT: " + u.getRut() + ")"
            );
        }
        return u;
    }

    //actualizar el usuario y guardar los cambios realizados en el historial
    public boolean actualizarUsuarioConLog(int actorId, int idUsuario, String nombre, String apellido,
                                           String rut, String nuevaContrasenaNullable, boolean activo) {
        // estado anterior (para detalle)
        Usuario antes = obtenerPorId(idUsuario);

        boolean ok = actualizarUsuario(idUsuario, nombre, apellido, rut, nuevaContrasenaNullable, activo);

        if (ok) {
            StringBuilder det = new StringBuilder();
            // Compara valores antiguos con los nuevos para registrar los cambios
            if (antes != null) {
                if (!antes.getNombre().equals(nombre))
                    det.append("nombre: ").append(antes.getNombre()).append(" -> ").append(nombre).append("; ");
                if (!antes.getApellido().equals(apellido))
                    det.append("apellido: ").append(antes.getApellido()).append(" -> ").append(apellido).append("; ");
                if (antes.isActivo() != activo)
                    det.append("activo: ").append(antes.isActivo()).append(" -> ").append(activo).append("; ");
                String rutAntes = antes.getRut();
                String rutDesp = normalizaRut(rut);
                if ((rutAntes == null && rutDesp != null) ||
                    (rutAntes != null && !rutAntes.equals(rutDesp))) {
                    det.append("rut: ").append(rutAntes).append(" -> ").append(rutDesp).append("; ");
                }
            }
            if (nuevaContrasenaNullable != null && !nuevaContrasenaNullable.isBlank())
                det.append("password: actualizado; ");

            if (det.length() == 0) det.append("sin cambios relevantes informados");

            new HistorialDAO().log(actorId, "EDITAR_USUARIO", "USUARIO", idUsuario, det.toString());
        }
        return ok;
    }
    
   // validar rut 

//Calcular el verificador del rut chileno 
private static String dvChileno(String cuerpoSoloDigitos) {
    int sum = 0, factor = 2;
    for (int i = cuerpoSoloDigitos.length() - 1; i >= 0; i--) {
        int n = cuerpoSoloDigitos.charAt(i) - '0';
        sum += n * factor;
        factor = (factor == 7) ? 2 : factor + 1;
    }
    int resto = 11 - (sum % 11);
    if (resto == 11) return "0";
    if (resto == 10) return "K";
    return String.valueOf(resto);
}

public Usuario buscarPorRutFlexible(String rutCrudo) {
    if (rutCrudo == null) return null;

    String limpio = rutCrudo.trim();
    String soloDigitos = limpio.replaceAll("\\D+", ""); 
    if (soloDigitos.isEmpty()) return null;

    String cuerpo = (soloDigitos.length() >= 9)
            ? soloDigitos.substring(0, soloDigitos.length() - 1)
            : soloDigitos;

    String dv = dvChileno(cuerpo);
    String cuerpoMasDvSinGuion = cuerpo + dv;   
    String cuerpoMasDvConGuion = cuerpo + "-" + dv; 
    final String sql = """
        SELECT id_usuario, nombre, apellido, activo, rut
        FROM public.usuarios
        WHERE activo = true
          AND rut IN (?, ?, ?, ?, ?)
        LIMIT 1
    """;

    try (Connection cn = ConexionDB.conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, limpio);
        ps.setString(2, soloDigitos);        
        ps.setString(3, cuerpo);                 
        ps.setString(4, cuerpoMasDvSinGuion);
        ps.setString(5, cuerpoMasDvConGuion);    

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        null,
                        rs.getBoolean("activo"),
                        rs.getString("rut")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

// Compatibilidad con llamadas existentes
public Usuario buscarPorRut(String rut) {
    return buscarPorRutFlexible(rut);
}
   
  

    
}
