package com.panaderia.modelo;

public class galleta extends producto {

    private boolean tieneChispas;
    
    public galleta(int idProducto, String nombre, int stock, double costo, double precio, boolean tieneChispas) {
        super(idProducto, nombre, stock, costo, precio);
        this.tieneChispas = tieneChispas;
        setTipo("Galleta"); 
    }

    public boolean gettieneChispas() {
        return tieneChispas;
    }

    public void settieneChispas(boolean tieneChispas) {
        this.tieneChispas = tieneChispas;
    }

    @Override
    public String obtenerDescripcionDetallada() {
        String base = super.obtenerDescripcionDetallada();
        return base + " - Galleta" + (tieneChispas ? " con chispas" : " sin chispas");
    }

    @Override
    public String toString() {
        return super.toString() + ", galleta{" +
                "tieneChispas='" + tieneChispas + '\'' +
                '}';
    }

}
