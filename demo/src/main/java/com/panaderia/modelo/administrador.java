package com.panaderia.modelo;

public class administrador extends usuario{

    private String clave;
    
    public administrador(int id, String nombre, String correo, String clave) {
        super(id, nombre, correo);
        this.clave = clave;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    // public void realizarCambiosEnElSistema() {
    //     // Implementa la lógica para cambios administrativos
    //     System.out.println("Cambios realizados en el sistema por el administrador.");
    // }

    @Override
    public String toString() {
        return "Administrador{" +
                "usuario=" + super.toString() +
                ", clave='" + clave + '\'' +
                '}';
    }
}
