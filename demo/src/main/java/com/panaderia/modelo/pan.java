package com.panaderia.modelo;

public class pan extends producto {

    private boolean tieneQueso;
    
    public pan(int idProducto, String nombre, int stock, double costo, double precio, boolean tieneQueso) {
        super(idProducto, nombre, stock, costo, precio);
        this.tieneQueso = tieneQueso;
    }

    // Getters y Setters
    public boolean gettieneQueso() {
        return tieneQueso;
    }

    public void settieneQueso(boolean tieneQueso) {
        this.tieneQueso = tieneQueso;
    }

    @Override
    public String toString() {
        return super.toString() + ", Pan{" +
                "tieneQueso='" + tieneQueso + '\'' +
                '}';
    }

}

