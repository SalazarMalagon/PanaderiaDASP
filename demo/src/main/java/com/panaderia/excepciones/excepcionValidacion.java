package com.panaderia.excepciones;

public class excepcionValidacion extends Exception {

    public excepcionValidacion() {
        super("Error de validación en el modelo.");
    }

    public excepcionValidacion(String mensaje) {
        super(mensaje);
    }

}

// if (precioVenta <= 0) {
//     throw new ExcepcionPrecioVentaInvalido("El precio de venta debe ser mayor a cero.");
// }

// if (stock < 0) {
//     throw new ExcepcionStockNegativo("El stock no puede ser negativo.");
// }

// if (costo < 0) {
//     throw new ExcepcionCostoInvalido("El costo debe ser mayor o igual a cero.");
// }
        
        
