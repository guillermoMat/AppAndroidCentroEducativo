package com.example.primeravez.Models;

public class Estudiante {
    private String nombre;
    private String apellido;
    private int legajo;
    private String correo;

    public Estudiante(){

    }
    public Estudiante( String nombre, String apellido, int legajo, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.correo = correo;
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

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
