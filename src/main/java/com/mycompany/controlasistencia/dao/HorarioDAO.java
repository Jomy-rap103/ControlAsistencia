package com.mycompany.controlasistencia.dao;

import com.mycompany.controlasistencia.config.ConexionDB;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {

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
                while (rs.next()) rows.add(new Object[]{ rs.getString(1), rs.getString(2), rs.getString(3) });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rows;
    }

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
            ps.setString(2, diaSemana);
            ps.setObject(3, entrada);
            ps.setObject(4, salida);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean borrarHorarioDia(int usuarioId, String diaSemana) {
        String sql = "DELETE FROM public.horarios WHERE usuario_id=? AND dia_semana=?::public.dia_semana";
        try (Connection c = ConexionDB.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, diaSemana);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
