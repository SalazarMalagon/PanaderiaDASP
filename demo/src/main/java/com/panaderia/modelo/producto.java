package com.panaderia.modelo;

public class producto {
    private int idProducto;
    private String nombre;
    private int stock;
    private double precio;
    private double costo;

    public producto(int idProducto, String nombre, int stock, double costo, double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.costo = costo;
        this.precio = precio;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", costo=" + costo +
                '}';
    }

}
