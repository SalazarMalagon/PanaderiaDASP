package com.panaderia.modelo;

/**
 * Clase que representa una galleta en el sistema.
 * Extiende de la clase `producto` y añade atributos y métodos específicos para las galletas.
 */
public class galleta extends producto {

    private boolean tieneChispas; // Indica si la galleta tiene chispas de chocolate

    /**
     * Constructor de la clase galleta.
     * Inicializa los atributos heredados de `producto` y el atributo específico `tieneChispas`.
     * @param idProducto El ID del producto.
     * @param nombre El nombre de la galleta.
     * @param stock La cantidad disponible en stock.
     * @param costo El costo de la galleta.
     * @param precio El precio de venta de la galleta.
     * @param tieneChispas Indica si la galleta tiene chispas de chocolate.
     */
    public galleta(int idProducto, String nombre, int stock, double costo, double precio, boolean tieneChispas) {
        super(idProducto, nombre, stock, costo, precio);
        this.tieneChispas = tieneChispas;
        setTipo("Galleta"); // Establece el tipo del producto como "Galleta"
    }

    /**
     * Obtiene si la galleta tiene chispas de chocolate.
     * @return `true` si la galleta tiene chispas, `false` en caso contrario.
     */
    public boolean getTieneChispas() {
        return tieneChispas;
    }

    /**
     * Establece si la galleta tiene chispas de chocolate.
     * @param tieneChispas `true` si la galleta tiene chispas, `false` en caso contrario.
     */
    public void setTieneChispas(boolean tieneChispas) {
        this.tieneChispas = tieneChispas;
    }

    /**
     * Obtiene una descripción detallada de la galleta.
     * Incluye información básica del producto y si tiene chispas o no.
     * @return Una cadena con la descripción detallada de la galleta.
     */
    @Override
    public String obtenerDescripcionDetallada() {
        String base = super.obtenerDescripcionDetallada(); // Llama al método de la clase padre
        return base + " - Galleta" + (tieneChispas ? " con chispas" : " sin chispas");
    }

    /**
     * Representación en forma de cadena del objeto galleta.
     * Incluye los datos del producto y si tiene chispas o no.
     * @return Una cadena con la información de la galleta.
     */
    @Override
    public String toString() {
        return super.toString() + ", galleta{" +
                "tieneChispas='" + tieneChispas + '\'' +
                '}';
    }
}