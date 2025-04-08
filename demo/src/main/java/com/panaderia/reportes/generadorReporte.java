package com.panaderia.reportes;

import com.panaderia.modelo.producto;
import com.panaderia.modelo.pan;
import com.panaderia.modelo.galleta;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class generadorReporte {

    public void generarCSV(List<producto> productos) {
        generarCSV(productos, "reporte_productos.csv");
    }

    public void generarCSV(List<producto> productos, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            writer.write("REPORTE DE PRODUCTOS - PANADERÍA\n");
            writer.write("Fecha de generación: " + ahora.format(formatter) + "\n\n");
            
            writer.write("ID,Nombre,Stock,Precio,Costo,Margen (%),Tipo,Características,Valor de inventario\n");
            
            double valorTotalInventario = 0;
            int totalProductos = 0;
            
            for (producto p : productos) {
                String tipo = "Genérico";
                String caracteristicas = "N/A";
                
                if (p instanceof pan) {
                    tipo = "Pan";
                    caracteristicas = "Queso: " + (((pan) p).getTieneQueso() ? "Sí" : "No");
                } else if (p instanceof galleta) {
                    tipo = "Galleta";
                    caracteristicas = "Chispas: " + (((galleta) p).getTieneChispas() ? "Sí" : "No");
                }
                
                double margen = ((p.getPrecio() - p.getCosto()) / p.getCosto()) * 100;
                String margenFormateado = String.format("%.2f", margen);
                
                double valorInventario = p.getStock() * p.getPrecio();
                valorTotalInventario += valorInventario;
                totalProductos += p.getStock();
                
                writer.write(p.getIdProducto() + "," + 
                             p.getNombre() + "," +
                             p.getStock() + "," + 
                             p.getPrecio() + "," + 
                             p.getCosto() + "," +
                             margenFormateado + "," +
                             tipo + "," +
                             caracteristicas + "," +
                             valorInventario + "\n");
            }
            
            writer.write("\nRESUMEN\n");
            writer.write("Total de productos diferentes: " + productos.size() + "\n");
            writer.write("Total de unidades en inventario: " + totalProductos + "\n");
            writer.write("Valor total del inventario: $" + valorTotalInventario + "\n");
            
            System.out.println("✅ Reporte CSV generado con éxito en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte CSV: " + e.getMessage());
        }
    }
}