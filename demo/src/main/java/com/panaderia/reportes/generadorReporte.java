package com.panaderia.reportes;

import com.panaderia.modelo.producto;
import java.util.List;

public class generadorReporte {

    public void generarCSV(List<producto> productos) {
        // Lógica para generar un reporte CSV
        System.out.println("Generando reporte CSV...");
        for (producto p : productos) {
            // Ejemplo: Imprimir cada producto en formato CSV
            System.out.println(p.getIdProducto() + "," + p.getNombre() + "," + p.getStock() + "," + p.getPrecio() + "," + p.getCosto());
        }
    }

}
