
package com.mycompany.controlasistencia.modelo;

public class Usuario {
    private int id_Usuario;
    private String nombre;
    private String apellido;
    private String contra;
    private boolean activo;

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public Usuario() {} 
    public Usuario(int id_Usuario, String nombre, String apellido, String contra, boolean activo) {
        this.id_Usuario = id_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contra = contra;
        this.activo = activo;
    }
}
