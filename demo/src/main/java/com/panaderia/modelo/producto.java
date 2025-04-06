package com.panaderia.modelo;

import com.panaderia.excepciones.excepcionCostoInvalido;
import com.panaderia.excepciones.excepcionPrecioVentaInvalido;
import com.panaderia.excepciones.excepcionStockNegativo;

public class producto {
    private int idProducto;
    private String nombre;
    private int stock;
    private double precio;
    private double costo;
    private String tipo;

    public producto(int idProducto, String nombre, int stock, double costo, double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.costo = costo;
        this.precio = precio;
        this.tipo = "Generico";

        try {
            validarCosto();
            validarPrecioVenta();
            validarStockNoNegativo();
        } catch (Exception e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
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

    public void validarCosto() throws excepcionCostoInvalido {
        if (costo < 0) {
            throw new excepcionCostoInvalido("El costo debe ser mayor o igual a cero.");
        }
        if (costo > precio) {
            throw new excepcionCostoInvalido("El costo no puede ser mayor al precio de venta.");
        }
    }

    public void validarPrecioVenta() throws excepcionPrecioVentaInvalido {
        if (precio <= 0) {
            throw new excepcionPrecioVentaInvalido("El precio de venta debe ser mayor a cero.");
        }
        if (precio <= costo) {
            throw new excepcionPrecioVentaInvalido("El precio debe ser mayor al costo.");
        }
    }

    public void validarStockNoNegativo() throws excepcionStockNegativo {
        if (stock < 0) {
            throw new excepcionStockNegativo("El stock no puede ser negativo.");
        }
    }

    public String obtenerDescripcionDetallada() {
        return "Producto " + idProducto + ": " + nombre + 
               " - Stock: " + stock + 
               " - Costo: $" + costo +
               " - Precio: $" + precio;
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
