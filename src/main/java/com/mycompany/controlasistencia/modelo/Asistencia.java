
package com.mycompany.controlasistencia.modelo;


public class Asistencia {
    
        public static record AgregarAsistencia(
            int id_usuario,
            String nombre,
            String apellido,
            java.time.LocalDateTime entrada,
            java.time.LocalDateTime salida,
            java.time.LocalDateTime lastUpdate, //para ordenar el que se mueva hacia arriba
            boolean entradaFuera,
            boolean salidaFuera //Fuera es por fuera de su horario establecido
                    
            ) {}
    
    
}
