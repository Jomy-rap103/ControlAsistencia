
package com.mycompany.controlasistencia.modelo;


public class Roles {
    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Roles(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
        //3.- Esta linea sirve para mostrar en el combox
    @Override public String toString() { return nombre; }
        //3.- Fin del comentario importante 3
}
