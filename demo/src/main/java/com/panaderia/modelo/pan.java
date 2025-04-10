package com.panaderia.modelo;

/**
 * Clase que representa un pan en el sistema.
 * Extiende de la clase `producto` y añade atributos y métodos específicos para los panes.
 */
public class pan extends producto {

    private boolean tieneQueso; // Indica si el pan tiene queso

    /**
     * Constructor de la clase pan.
     * Inicializa los atributos heredados de `producto` y el atributo específico `tieneQueso`.
     * @param idProducto El ID del producto.
     * @param nombre El nombre del pan.
     * @param stock La cantidad disponible en stock.
     * @param costo El costo del pan.
     * @param precio El precio de venta del pan.
     * @param tieneQueso Indica si el pan tiene queso.
     */
    public pan(int idProducto, String nombre, int stock, double costo, double precio, boolean tieneQueso) {
        super(idProducto, nombre, stock, costo, precio);
        this.tieneQueso = tieneQueso;
        setTipo("Pan"); // Establece el tipo del producto como "Pan"
    }

    /**
     * Obtiene si el pan tiene queso.
     * @return `true` si el pan tiene queso, `false` en caso contrario.
     */
    public boolean getTieneQueso() {
        return tieneQueso;
    }

    /**
     * Establece si el pan tiene queso.
     * @param tieneQueso `true` si el pan tiene queso, `false` en caso contrario.
     */
    public void setTieneQueso(boolean tieneQueso) {
        this.tieneQueso = tieneQueso;
    }

    /**
     * Obtiene una descripción detallada del pan.
     * Incluye información básica del producto y si tiene queso o no.
     * @return Una cadena con la descripción detallada del pan.
     */
    @Override
    public String obtenerDescripcionDetallada() {
        String base = super.obtenerDescripcionDetallada(); // Llama al método de la clase padre
        return base + " - Pan" + (tieneQueso ? " con queso" : " sin queso");
    }

    /**
     * Representación en forma de cadena del objeto pan.
     * Incluye los datos del producto y si tiene queso o no.
     * @return Una cadena con la información del pan.
     */
    @Override
    public String toString() {
        return super.toString() + ", Pan{" +
                "tieneQueso='" + tieneQueso + '\'' +
                '}';
    }
}