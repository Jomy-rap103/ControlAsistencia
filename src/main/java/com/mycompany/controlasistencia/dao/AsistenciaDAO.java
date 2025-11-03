    package com.mycompany.controlasistencia.dao;

    import com.mycompany.controlasistencia.config.ConexionDB;
    import com.mycompany.controlasistencia.modelo.Asistencia;

    import java.sql.*;
    import java.time.*;
    import java.util.ArrayList;
    import java.util.List;



    public class AsistenciaDAO {

        private static final String ROL_PROFESOR  = "PROFESOR";
        private static final String ROL_ASISTENTE = "ASISTENTE";
        private static final int TOLERANCIA_MIN = 5; // minutos de tolerancia para entrada/salida


        public boolean existeEntradaHoy(int usuarioId, LocalDate hoy) {
            String sql = """
                SELECT 1
                FROM registros
                WHERE usuario_id = ?
                  AND tipo = 'E'
                  AND fecha_hora::date = ?
                LIMIT 1
            """;
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, usuarioId);
                ps.setDate(2, Date.valueOf(hoy));
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); //true por si existe una entrada hoy
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean existeSalidaHoy(int usuarioId, LocalDate hoy) {
            String sql = """
                SELECT 1
                FROM registros
                WHERE usuario_id = ?
                  AND tipo = 'S'
                  AND fecha_hora::date = ?
                LIMIT 1
            """;
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, usuarioId);
                ps.setDate(2, Date.valueOf(hoy));
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


        // Insert de entrada una vez por dia, marcar fuera de hora segun el horario del dia, no insertar si no hay horario
        public boolean registrarEntradaSiNoExiste(int usuarioId, LocalDateTime ahora) {
            boolean fuera = false;
            //Si no hay horario para el dia de la semana no registro la asistencia
            if (!existeHorarioParaDia (usuarioId, ahora.getDayOfWeek())) {
                return false;
        } 
            //Aqui fuera_de_hora llega despues de la hora programada + tolerancia
            try {
                //
                fuera = fueraDeHoraEntrada(usuarioId, ahora.toLocalTime(), ahora.getDayOfWeek());
            } catch (Exception e) {
                // si falla el cálculo, no bloqueo el registro
                fuera = false;
            }

            String sql = """
                INSERT INTO registros (usuario_id, fecha_hora, tipo, fuera_de_hora)
                SELECT ?, ?, 'E', ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM registros
                    WHERE usuario_id = ? AND tipo = 'E' AND fecha_hora::date = ?
                )
            """;
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, usuarioId);
                ps.setTimestamp(2, Timestamp.valueOf(ahora));
                ps.setBoolean(3, fuera);
                ps.setInt(4, usuarioId);
                ps.setDate(5, Date.valueOf(ahora.toLocalDate()));
                return ps.executeUpdate() > 0; //Insert solo si no existia entrada hoy
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        //Insert de salida una vez por dia y requiere que haya una entrada registrada
        public boolean registrarSalidaSiNoExiste(int usuarioId, LocalDateTime ahora) {
            
            if(!existeHorarioParaDia(usuarioId, ahora.getDayOfWeek() )) {
                return false;
            }
            
            boolean fuera = false;
            try {
                fuera = fueraDeHoraSalida(usuarioId, ahora.toLocalTime(), ahora.getDayOfWeek());
            } catch (Exception e) {
                fuera = false;
            }

            String sql = """
                INSERT INTO registros (usuario_id, fecha_hora, tipo, fuera_de_hora)
                SELECT ?, ?, 'S', ?
                WHERE EXISTS (
                    SELECT 1 FROM registros
                    WHERE usuario_id = ? AND tipo = 'E' AND fecha_hora::date = ?
                )
                  AND NOT EXISTS (
                    SELECT 1 FROM registros
                    WHERE usuario_id = ? AND tipo = 'S' AND fecha_hora::date = ?
                  )
            """;
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                LocalDate hoy = ahora.toLocalDate();
                ps.setInt(1, usuarioId);
                ps.setTimestamp(2, Timestamp.valueOf(ahora));
                ps.setBoolean(3, fuera);
                ps.setInt(4, usuarioId);
                ps.setDate(5, Date.valueOf(hoy));
                ps.setInt(6, usuarioId);
                ps.setDate(7, Date.valueOf(hoy));
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        private boolean fueraDeHoraEntrada(int usuarioId, LocalTime ahora, DayOfWeek dow) {
            LocalTime hora = obtenerHora(usuarioId, dow, true);
            System.out.println("[F.H.] ENTRADA ahora=" + ahora + " horario=" + hora + " dia=" + dow);
            if (hora == null) return false;
            boolean fuera = ahora.isAfter(hora.plusMinutes(TOLERANCIA_MIN));
            System.out.println("[F.H.] ENTRADA fuera=" + fuera);
            return fuera;
    }


        private boolean fueraDeHoraSalida(int usuarioId, LocalTime ahora, DayOfWeek dow) {
            LocalTime hora = obtenerHora(usuarioId, dow, false);
            if (hora == null) return false;
            // salida fuera de hora si sale antes de la pactada (con tolerancia)
            return ahora.isBefore(hora.minusMinutes(TOLERANCIA_MIN));
        }

        private LocalTime obtenerHora(int usuarioId, DayOfWeek dow, boolean entrada) {
        String diaEnum = mapDiaSemana(dow); // LUNES..SABADO, null si DOMINGO
        if (diaEnum == null) return null;

        String col = entrada ? "hora_entrada" : "hora_salida";
        String sql = "SELECT " + col + " FROM horarios " +
                     "WHERE usuario_id = ? AND dia_semana = ?::public.dia_semana " + // <<--- CAST AQUÍ
                     "LIMIT 1";

        try (var con = ConexionDB.conectar();
             var ps  = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, diaEnum);  // "LUNES"..."SABADO"
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    Time t = rs.getTime(1);
                    return t != null ? t.toLocalTime() : null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


        // ENUM de la base de datos (tus valores exactos)
        private String mapDiaSemana(DayOfWeek d) {
            return switch (d) {
                case MONDAY    -> "LUNES";
                case TUESDAY   -> "MARTES";
                case WEDNESDAY -> "MIERCOLES";
                case THURSDAY  -> "JUEVES";
                case FRIDAY    -> "VIERNES";
                case SATURDAY  -> "SABADO";
                default        -> null; 
            };
        }

        //Aqui va la logica para actualizar la tabla de la VistaPrincipal

        public List<Asistencia.AgregarAsistencia> listarResumenFecha (LocalDate fecha) {
            String sql = """
                         SELECT
                           u.id_usuario,
                           u.nombre,
                           u.apellido,
                           MIN(r.fecha_hora) FILTER (WHERE r.tipo='E') AS entrada,
                           MAX(r.fecha_hora) FILTER (WHERE r.tipo='S') AS salida,
                           COALESCE(BOOL_OR(r.fuera_de_hora) FILTER (WHERE r.tipo = 'S'), FALSE) AS salida_fuera,
                           COALESCE(BOOL_OR(r.fuera_de_hora) FILTER (WHERE r.tipo = 'E'), FALSE) AS entrada_fuera,
                           GREATEST(
                             COALESCE(MAX(r.fecha_hora) FILTER (WHERE r.tipo='S'), TIMESTAMP 'epoch'),
                             COALESCE(MIN(r.fecha_hora) FILTER (WHERE r.tipo='E'), TIMESTAMP 'epoch')
                           ) AS last_update
                         FROM usuarios u
                         JOIN registros r ON r.usuario_id = u.id_usuario
                         WHERE r.fecha_hora::date = ?
                         GROUP BY u.id_usuario, u.nombre, u.apellido
                         ORDER BY last_update DESC
                         """;

            List <Asistencia.AgregarAsistencia> out = new ArrayList();
            try (var conn = ConexionDB.conectar();
                 var ps   = conn.prepareStatement(sql)) {

                ps.setDate(1, java.sql.Date.valueOf(fecha));

                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        var entrada = rs.getTimestamp("entrada") == null ? null : rs.getTimestamp("entrada").toLocalDateTime();
                        var salida  = rs.getTimestamp("salida")  == null ? null : rs.getTimestamp("salida").toLocalDateTime();
                        var lastUp  = rs.getTimestamp("last_update").toLocalDateTime();

                        out.add(new Asistencia.AgregarAsistencia(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            entrada,
                            salida,
                            lastUp,
                            rs.getBoolean("entrada_fuera"),
                            rs.getBoolean("salida_fuera")
                        ));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return out;
            }


        public List<java.time.LocalDate> listarFechasDisponibles (int maxDias) {
        String sql = """
                     SELECT DISTINCT (fecha_hora::date) AS fecha
                     FROM registros
                     ORDER BY fecha DESC
                     LIMIT ?
                     """;

        var out = new ArrayList<java.time.LocalDate>();
        try (var con = ConexionDB.conectar();
                var ps =con.prepareStatement(sql)) {
            ps.setInt(1, maxDias); 
            try (var rs = ps.executeQuery()) {
                while (rs.next()) out.add(rs.getDate("fecha").toLocalDate());
            } 
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
            return out;
        }


        // ===== Reportes =====

    // Entrada/Salida del día (valores reales leídos de registros)
    public static record ESDia(LocalDateTime entrada, LocalDateTime salida) {}

    // Reporte diario: programadas = LocalTime, reales = LocalDateTime
    public static record ReporteDia(
            LocalTime entradaProg,
            LocalDateTime entradaReal,
            int minutosTarde,
            LocalTime salidaProg,
            LocalDateTime salidaReal,
            int minutosAnticipo,
            String motivo
    ) {}

    public static record ReporteMes(
            YearMonth periodo,
            int minutosTardeTotal,
            int minutosAnticipoTotal
    ) {}

    // Exponer horario programado (envuelve a tu método privado obtenerHora)
    public LocalTime obtenerHoraPublic(int usuarioId, DayOfWeek dow, boolean entrada) {
        return obtenerHora(usuarioId, dow, entrada);
    }



    // Entrada/Salida (MIN/MAX) del día
    public java.util.Optional<ESDia> obtenerESDia(int usuarioId, LocalDate fecha) {
        String sql = """
            SELECT
                MIN(r.fecha_hora) FILTER (WHERE r.tipo='E') AS entrada,
                MAX(r.fecha_hora) FILTER (WHERE r.tipo='S') AS salida
            FROM registros r
            WHERE r.usuario_id = ?
              AND r.fecha_hora::date = ?
        """;
        try (var con = ConexionDB.conectar();
             var ps  = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setDate(2, java.sql.Date.valueOf(fecha));
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var e = rs.getTimestamp("entrada");
                    var s = rs.getTimestamp("salida");
                    var ent = (e == null) ? null : e.toLocalDateTime();
                    var sal = (s == null) ? null : s.toLocalDateTime();
                    if (ent != null || sal != null) return java.util.Optional.of(new ESDia(ent, sal));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return java.util.Optional.empty();
    }

    // ===== Cálculos de minutos  =====
    private int minutosTarde(LocalDateTime entradaReal, LocalTime horaProg, int toleranciaMin) {
        if (entradaReal == null || horaProg == null) return 0;
        var fecha = entradaReal.toLocalDate();
        var umbral = LocalDateTime.of(fecha, horaProg).plusMinutes(toleranciaMin);
        if (entradaReal.isAfter(umbral)) {
            long mins = java.time.Duration.between(umbral, entradaReal).toMinutes();
            return (int) Math.max(0, mins);
        }
        return 0;
    }

    private int minutosAnticipo(LocalDateTime salidaReal, LocalTime horaProg, int toleranciaMin) {
        if (salidaReal == null || horaProg == null) return 0;
        var fecha = salidaReal.toLocalDate();
        var umbral = LocalDateTime.of(fecha, horaProg).minusMinutes(toleranciaMin);
        if (salidaReal.isBefore(umbral)) {
            long mins = java.time.Duration.between(salidaReal, umbral).toMinutes();
            return (int) Math.max(0, mins);
        }
        return 0;
    }

    // ===== Reporte diario =====
    public ReporteDia generarReporteDia(int usuarioId, LocalDate fecha) {
        var dow   = fecha.getDayOfWeek();
        var horaE = obtenerHoraPublic(usuarioId, dow, true);   
        var horaS = obtenerHoraPublic(usuarioId, dow, false);  

        var esOpt = obtenerESDia(usuarioId, fecha);
        LocalDateTime entrada = null, salida = null;
        if (esOpt.isPresent()) {
            entrada = esOpt.get().entrada();
            salida  = esOpt.get().salida();
        }

        int tMin = minutosTarde(entrada, horaE, TOLERANCIA_MIN);
        int aMin = minutosAnticipo(salida,  horaS, TOLERANCIA_MIN);

        String motivo;
        if (tMin > 0 && aMin > 0)
            motivo = "Entrada tardía (" + tMin + " min) y salida anticipada (" + aMin + " min).";
        else if (tMin > 0)
            motivo = "Entrada tardía (" + tMin + " min).";
        else if (aMin > 0)
            motivo = "Salida anticipada (" + aMin + " min).";
        else
            motivo = "Sin faltas registradas.";

        return new ReporteDia(horaE, entrada, tMin, horaS, salida, aMin, motivo);
    }

    // ===== Reporte mensual =====
    public ReporteMes generarReporteMes(int usuarioId, YearMonth ym) {
        var desde     = ym.atDay(1);
        var hastaExcl = ym.plusMonths(1).atDay(1); 

        String sql = """
            SELECT r.fecha_hora::date AS fecha,
                   MIN(r.fecha_hora) FILTER (WHERE r.tipo='E') AS entrada,
                   MAX(r.fecha_hora) FILTER (WHERE r.tipo='S') AS salida
            FROM registros r
            WHERE r.usuario_id = ?
              AND r.fecha_hora >= ?::date
              AND r.fecha_hora <  ?::date
            GROUP BY r.fecha_hora::date
            ORDER BY fecha
        """;

        int totalTarde = 0;
        int totalAnticipo = 0;

        try (var con = ConexionDB.conectar();
             var ps  = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setDate(2, java.sql.Date.valueOf(desde));
            ps.setDate(3, java.sql.Date.valueOf(hastaExcl));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var f  = rs.getDate("fecha").toLocalDate();
                    var eT = rs.getTimestamp("entrada");
                    var sT = rs.getTimestamp("salida");
                    var e  = (eT == null) ? null : eT.toLocalDateTime();
                    var s  = (sT == null) ? null : sT.toLocalDateTime();

                    var he = obtenerHoraPublic(usuarioId, f.getDayOfWeek(), true);
                    var hs = obtenerHoraPublic(usuarioId, f.getDayOfWeek(), false);

                    totalTarde    += minutosTarde(e, he, TOLERANCIA_MIN);
                    totalAnticipo += minutosAnticipo(s, hs, TOLERANCIA_MIN);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ReporteMes(ym, totalTarde, totalAnticipo);
    }

    
    
    private java.util.List<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia>
    listarResumenFechaPorRol(java.time.LocalDate fecha, String rol) {

        String sql = """
            SELECT
              u.id_usuario,
              u.nombre,
              u.apellido,
              MIN(r.fecha_hora) FILTER (WHERE r.tipo='E') AS entrada,
              MAX(r.fecha_hora) FILTER (WHERE r.tipo='S') AS salida,
              COALESCE(BOOL_OR(r.fuera_de_hora) FILTER (WHERE r.tipo = 'S'), FALSE) AS salida_fuera,
              COALESCE(BOOL_OR(r.fuera_de_hora) FILTER (WHERE r.tipo = 'E'), FALSE) AS entrada_fuera,
              GREATEST(
                COALESCE(MAX(r.fecha_hora) FILTER (WHERE r.tipo='S'), TIMESTAMP 'epoch'),
                COALESCE(MIN(r.fecha_hora) FILTER (WHERE r.tipo='E'), TIMESTAMP 'epoch')
              ) AS last_update
            FROM usuarios u
            JOIN registros r ON r.usuario_id = u.id_usuario
            WHERE r.fecha_hora::date = ?
              AND UPPER(u.rol) = UPPER(?)
            GROUP BY u.id_usuario, u.nombre, u.apellido
            ORDER BY last_update DESC
        """;

        var out = new java.util.ArrayList<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia>();
        try (var con = com.mycompany.controlasistencia.config.ConexionDB.conectar();
             var ps  = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ps.setString(2, rol);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var entrada = rs.getTimestamp("entrada") == null ? null : rs.getTimestamp("entrada").toLocalDateTime();
                    var salida  = rs.getTimestamp("salida")  == null ? null : rs.getTimestamp("salida").toLocalDateTime();
                    var lastUp  = rs.getTimestamp("last_update").toLocalDateTime();

                    out.add(new com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        entrada,
                        salida,
                        lastUp,
                        rs.getBoolean("entrada_fuera"),
                        rs.getBoolean("salida_fuera")
                    ));
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    // listar por ROL
    private java.util.List<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia>
    listarResumenPorRol(java.time.LocalDate fecha, String rolBuscado) {

        final String sql = """
            SELECT
              u.id_usuario,
              u.nombre,
              u.apellido,
              MIN(r.fecha_hora) FILTER (WHERE r.tipo='E') AS entrada,
              MAX(r.fecha_hora) FILTER (WHERE r.tipo='S') AS salida,
              COALESCE(BOOL_OR(r.fuera_de_hora) FILTER (WHERE r.tipo='E'), FALSE) AS entrada_fuera,
              COALESCE(BOOL_OR(r.fuera_de_hora) FILTER (WHERE r.tipo='S'), FALSE) AS salida_fuera,
              GREATEST(
                COALESCE(MAX(r.fecha_hora) FILTER (WHERE r.tipo='S'), TIMESTAMP 'epoch'),
                COALESCE(MIN(r.fecha_hora) FILTER (WHERE r.tipo='E'), TIMESTAMP 'epoch')
              ) AS last_update
            FROM usuarios u
            JOIN registros r           ON r.usuario_id = u.id_usuario
            LEFT JOIN usuario_roles ur ON ur.usuario_id = u.id_usuario
            LEFT JOIN roles ro         ON ro.id_rol     = ur.rol_id
            WHERE r.fecha_hora::date = ?
              AND ro.nombre ILIKE ?
            GROUP BY u.id_usuario, u.nombre, u.apellido
            ORDER BY last_update DESC
        """;

        var out = new java.util.ArrayList<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia>();
        try (var con = com.mycompany.controlasistencia.config.ConexionDB.conectar();
             var ps  = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ps.setString(2, rolBuscado);  

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var entrada = rs.getTimestamp("entrada") == null ? null : rs.getTimestamp("entrada").toLocalDateTime();
                    var salida  = rs.getTimestamp("salida")  == null ? null : rs.getTimestamp("salida").toLocalDateTime();
                    var lastUp  = rs.getTimestamp("last_update").toLocalDateTime();

                    out.add(new com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        entrada,
                        salida,
                        lastUp,
                        rs.getBoolean("entrada_fuera"),
                        rs.getBoolean("salida_fuera")
                    ));
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    public java.util.List<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia> listarResumenProfesores(java.time.LocalDate fecha) {
        return listarResumenPorRol(fecha, "PROFESOR");
    }
    public java.util.List<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia> listarResumenAsistentes(java.time.LocalDate fecha) {
        return listarResumenPorRol(fecha, "ASISTENTE");
    }
    

public boolean actualizarHorarioConLog(
        Integer actorId,
        int usuarioId,
        java.time.LocalDate fecha,
        java.time.LocalTime nuevaEntradaNullable,
        java.time.LocalTime nuevaSalidaNullable
) {
    String sel = """
        SELECT hora_entrada, hora_salida
        FROM registros
        WHERE usuario_id = ? AND fecha = ?
        FOR UPDATE
    """;
    String upd = """
        UPDATE registros
        SET hora_entrada = COALESCE(?, hora_entrada),
            hora_salida  = COALESCE(?, hora_salida)
        WHERE usuario_id = ? AND fecha = ?
    """;

    try (var con = com.mycompany.controlasistencia.config.ConexionDB.conectar()) {
        con.setAutoCommit(false);

        java.time.LocalTime antesE = null, antesS = null;

        try (var ps = con.prepareStatement(sel)) {
            ps.setInt(1, usuarioId);
            ps.setObject(2, fecha);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    antesE = (java.time.LocalTime) rs.getObject("hora_entrada");
                    antesS = (java.time.LocalTime) rs.getObject("hora_salida");
                } else {
                    con.rollback();
                    return false;
                }
            }
        }

        try (var ps = con.prepareStatement(upd)) {
            if (nuevaEntradaNullable == null) ps.setNull(1, java.sql.Types.TIME);
            else ps.setObject(1, nuevaEntradaNullable);
            if (nuevaSalidaNullable == null) ps.setNull(2, java.sql.Types.TIME);
            else ps.setObject(2, nuevaSalidaNullable);
            ps.setInt(3, usuarioId);
            ps.setObject(4, fecha);

            int rows = ps.executeUpdate();
            if (rows != 1) { con.rollback(); return false; }
        }
        
        // solo qué se modificó: entrada/salida)
        boolean cambioE = (nuevaEntradaNullable != null) &&
                          !java.util.Objects.equals(antesE, nuevaEntradaNullable);
        boolean cambioS = (nuevaSalidaNullable != null) &&
                          !java.util.Objects.equals(antesS, nuevaSalidaNullable);

        if (actorId != null && (cambioE || cambioS)) {
            StringBuilder det = new StringBuilder();
            if (cambioE)
                det.append("entrada: ").append(antesE).append(" -> ").append(nuevaEntradaNullable).append("; ");
            if (cambioS)
                det.append("salida: ").append(antesS).append(" -> ").append(nuevaSalidaNullable).append("; ");

            new com.mycompany.controlasistencia.dao.HistorialDAO()
                .logEditarHorario(actorId, usuarioId, det.toString().trim());
        }

        con.commit();
        con.setAutoCommit(true);
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

private boolean existeHorarioParaDia(int usuarioId, DayOfWeek dow) {
    String diaEnum = mapDiaSemana(dow); 
    if (diaEnum == null) return false;  
    String sql = """
        SELECT 1
        FROM horarios
        WHERE usuario_id = ?
          AND dia_semana = ?::public.dia_semana
          AND (hora_entrada IS NOT NULL OR hora_salida IS NOT NULL)
        LIMIT 1
    """;
    try (var con = ConexionDB.conectar();
         var ps  = con.prepareStatement(sql)) {
        ps.setInt(1, usuarioId);
        ps.setString(2, diaEnum);
        try (var rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}







    } //no borrar este {
