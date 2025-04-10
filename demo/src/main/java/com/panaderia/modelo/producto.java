package com.panaderia.modelo;

import com.panaderia.excepciones.excepcionCostoInvalido;
import com.panaderia.excepciones.excepcionPrecioVentaInvalido;
import com.panaderia.excepciones.excepcionStockNegativo;

/**
 * Clase que representa un producto genérico en el sistema.
 * Contiene atributos y métodos comunes para todos los productos.
 */
public class producto {
    private int idProducto; // Identificador único del producto
    private String nombre; // Nombre del producto
    private int stock; // Cantidad disponible en stock
    private double precio; // Precio de venta del producto
    private double costo; // Costo del producto
    private String tipo; // Tipo del producto (por defecto "Generico")

    /**
     * Constructor de la clase producto.
     * Inicializa los atributos del producto y valida sus valores.
     * @param idProducto El ID del producto.
     * @param nombre El nombre del producto.
     * @param stock La cantidad disponible en stock.
     * @param costo El costo del producto.
     * @param precio El precio de venta del producto.
     */
    public producto(int idProducto, String nombre, int stock, double costo, double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.costo = costo;
        this.precio = precio;
        this.tipo = "Generico";

        // Validar los valores iniciales del producto
        try {
            validarCosto();
            validarPrecioVenta();
            validarStockNoNegativo();
        } catch (Exception e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }
    }

    /**
     * Obtiene el tipo del producto.
     * @return El tipo del producto.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del producto.
     * @param tipo El tipo del producto.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el ID del producto.
     * @return El ID del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto.
     * @param idProducto El nuevo ID del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la cantidad disponible en stock.
     * @return La cantidad en stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad disponible en stock.
     * @param stock La nueva cantidad en stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el precio de venta del producto.
     * @return El precio de venta.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de venta del producto.
     * @param precio El nuevo precio de venta.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el costo del producto.
     * @return El costo del producto.
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Establece el costo del producto.
     * @param costo El nuevo costo del producto.
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * Valida que el costo del producto sea válido.
     * @throws excepcionCostoInvalido Si el costo es menor a 0 o mayor al precio de venta.
     */
    public void validarCosto() throws excepcionCostoInvalido {
        if (costo < 0) {
            throw new excepcionCostoInvalido("El costo debe ser mayor o igual a cero.");
        }
        if (costo > precio) {
            throw new excepcionCostoInvalido("El costo no puede ser mayor al precio de venta.");
        }
    }

    /**
     * Valida que el precio de venta del producto sea válido.
     * @throws excepcionPrecioVentaInvalido Si el precio es menor o igual a 0 o menor al costo.
     */
    public void validarPrecioVenta() throws excepcionPrecioVentaInvalido {
        if (precio <= 0) {
            throw new excepcionPrecioVentaInvalido("El precio de venta debe ser mayor a cero.");
        }
        if (precio <= costo) {
            throw new excepcionPrecioVentaInvalido("El precio debe ser mayor al costo.");
        }
    }

    /**
     * Valida que el stock del producto no sea negativo.
     * @throws excepcionStockNegativo Si el stock es menor a 0.
     */
    public void validarStockNoNegativo() throws excepcionStockNegativo {
        if (stock < 0) {
            throw new excepcionStockNegativo("El stock no puede ser negativo.");
        }
    }

    /**
     * Obtiene una descripción detallada del producto.
     * Incluye el ID, nombre, stock, costo y precio.
     * @return Una cadena con la descripción detallada del producto.
     */
    public String obtenerDescripcionDetallada() {
        return "Producto " + idProducto + ": " + nombre + 
               " - Stock: " + stock + 
               " - Costo: $" + costo +
               " - Precio: $" + precio;
    }

    /**
     * Representación en forma de cadena del objeto producto.
     * Incluye los datos básicos del producto.
     * @return Una cadena con la información del producto.
     */
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