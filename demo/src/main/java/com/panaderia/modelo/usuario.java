package com.panaderia.modelo;

public class usuario {

    private int idUsuario;
    private String nombre;
    private String correo;
    
    public usuario(int idUsuario, String nombre, String correo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters y Setters
    public int getidUsuario() {
        return idUsuario;
    }
    
    public void setidUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getnombre() {
        return nombre;
    }
    
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getcorreo() {
        return correo;
    }
    
    public void setcorreo(String correo) {
        this.correo = correo;
    }
    // public boolean autenticar(String correo, String password) {
    //     // Lógica de autenticación (por ejemplo, verificar contra datos almacenados)
    //     return this.correo.equals(correo);
    // }

    @Override
    public String toString() {
        return "usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }

}
