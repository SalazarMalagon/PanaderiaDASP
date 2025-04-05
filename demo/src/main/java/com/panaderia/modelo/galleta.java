package com.panaderia.modelo;

public class galleta extends producto {

    private boolean tieneChispas;
    
    public galleta(int idProducto, String nombre, int stock, double costo, double precio, boolean tieneChispas) {
        super(idProducto, nombre, stock, costo, precio);
        this.tieneChispas = tieneChispas;
    }

    // Getters y Setters
    public boolean gettieneChispas() {
        return tieneChispas;
    }

    public void settieneChispas(boolean tieneChispas) {
        this.tieneChispas = tieneChispas;
    }

    @Override
    public String toString() {
        return super.toString() + ", galleta{" +
                "tieneChispas='" + tieneChispas + '\'' +
                '}';
    }

}
