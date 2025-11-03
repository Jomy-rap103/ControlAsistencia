
package com.mycompany.controlasistencia.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionDB {
     private static final Dotenv dotenv = Dotenv.load(); 

    private static final String URL  = getenv("DB_URL");
    private static final String USER = getenv("DB_USER");
    private static final String PASS = getenv("DB_PASSWORD");

    private static String getenv(String key) {
        String v = dotenv.get(key);
        if (v == null) v = System.getenv(key);
        return v;
    }

    public static Connection conectar() throws SQLException {
        if (URL == null || USER == null || PASS == null) {
            throw new IllegalStateException("Faltan DB_URL, DB_USER o DB_PASSWORD (.env o entorno).");
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }
}
