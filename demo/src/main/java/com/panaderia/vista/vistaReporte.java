package com.panaderia.vista;

/**
 * Clase que representa la vista para la generación de reportes.
 * Proporciona métodos para mostrar mensajes relacionados con el estado de la generación de reportes.
 */
public class vistaReporte {

    /**
     * Muestra un mensaje indicando que el reporte ha sido generado correctamente.
     * Este mensaje se imprime en la consola.
     */
    public void mostrarMensajeReporteGenerado() {
        System.out.println("✅ El reporte ha sido generado correctamente.");
    }

    /**
     * Muestra un mensaje indicando que hubo un error al generar el reporte.
     * Este mensaje se imprime en la consola.
     */
    public void mostrarMensajeErrorReporte() {
        System.out.println("❌ Hubo un error al generar el reporte.");
    }
}