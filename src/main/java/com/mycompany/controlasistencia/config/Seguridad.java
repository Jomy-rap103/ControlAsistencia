
package com.mycompany.controlasistencia.config;

import org.mindrot.jbcrypt.BCrypt;


public class Seguridad {
        private Seguridad() {}

    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }
    public static boolean verificar(String plain, String hash) {
        if (hash == null || hash.isEmpty()) return false;
        return BCrypt.checkpw(plain, hash);
    }
}
