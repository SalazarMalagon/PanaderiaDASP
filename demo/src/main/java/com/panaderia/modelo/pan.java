package com.panaderia.modelo;

public class pan extends producto {

    private boolean tieneQueso;
    
    public pan(int idProducto, String nombre, int stock, double costo, double precio, boolean tieneQueso) {
        super(idProducto, nombre, stock, costo, precio);
        this.tieneQueso = tieneQueso;
        setTipo("Pan");
    }

    public boolean gettieneQueso() {
        return tieneQueso;
    }

    public void settieneQueso(boolean tieneQueso) {
        this.tieneQueso = tieneQueso;
    }


    @Override
    public String obtenerDescripcionDetallada() {
        String base = super.obtenerDescripcionDetallada();
        return base + " - Pan" + (tieneQueso ? " con queso" : " sin queso");
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Pan{" +
                "tieneQueso='" + tieneQueso + '\'' +
                '}';
    }

    

}

