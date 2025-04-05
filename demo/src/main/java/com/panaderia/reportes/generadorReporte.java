package com.panaderia.reportes;

import com.panaderia.modelo.producto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class generadorReporte {

    public void generarCSV(List<producto> productos) {
        try (FileWriter writer = new FileWriter("reporte_productos.csv")) {
        writer.write("ID,Nombre,Stock,Precio,Costo\n");
        for (producto p : productos) {
            writer.write(p.getIdProducto() + "," + p.getNombre() + "," +
                         p.getStock() + "," + p.getPrecio() + "," + p.getCosto() + "\n");
        }
            System.out.println("Reporte CSV generado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al generar reporte CSV: " + e.getMessage());
        }
    }

}
