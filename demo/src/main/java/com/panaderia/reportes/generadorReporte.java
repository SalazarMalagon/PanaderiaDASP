package com.panaderia.reportes;

import com.panaderia.modelo.producto;
import com.panaderia.modelo.pan;
import com.panaderia.modelo.galleta;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase encargada de generar reportes en formato CSV para los productos de la panadería.
 * Incluye información detallada de los productos y un resumen del inventario.
 */
public class generadorReporte {

    /**
     * Genera un reporte CSV con los productos y lo guarda en un archivo predeterminado.
     * @param productos Lista de productos a incluir en el reporte.
     */
    public void generarCSV(List<producto> productos) {
        generarCSV(productos, "reporte_productos.csv");
    }

    /**
     * Genera un reporte CSV con los productos y lo guarda en la ruta especificada.
     * @param productos Lista de productos a incluir en el reporte.
     * @param rutaArchivo Ruta donde se guardará el archivo CSV.
     */
    public void generarCSV(List<producto> productos, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            // Obtener la fecha y hora actual para incluirla en el reporte
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            // Escribir encabezado del reporte
            writer.write("REPORTE DE PRODUCTOS - PANADERÍA\n");
            writer.write("Fecha de generación: " + ahora.format(formatter) + "\n\n");
            
            // Escribir encabezados de las columnas
            writer.write("ID,Nombre,Stock,Precio,Costo,Margen (%),Tipo,Características,Valor de inventario\n");
            
            double valorTotalInventario = 0; // Acumulador para el valor total del inventario
            int totalProductos = 0; // Contador para el total de unidades en inventario
            
            // Iterar sobre la lista de productos y escribir sus detalles
            for (producto p : productos) {
                String tipo = "Genérico"; // Tipo del producto (por defecto)
                String caracteristicas = "N/A"; // Características adicionales (por defecto)
                
                // Determinar el tipo y características específicas según la clase del producto
                if (p instanceof pan) {
                    tipo = "Pan";
                    caracteristicas = "Queso: " + (((pan) p).getTieneQueso() ? "Sí" : "No");
                } else if (p instanceof galleta) {
                    tipo = "Galleta";
                    caracteristicas = "Chispas: " + (((galleta) p).getTieneChispas() ? "Sí" : "No");
                }
                
                // Calcular el margen de ganancia en porcentaje
                double margen = ((p.getPrecio() - p.getCosto()) / p.getCosto()) * 100;
                String margenFormateado = String.format("%.2f", margen);
                
                // Calcular el valor del inventario para este producto
                double valorInventario = p.getStock() * p.getPrecio();
                valorTotalInventario += valorInventario; // Acumular el valor total del inventario
                totalProductos += p.getStock(); // Acumular el total de unidades
                
                // Escribir los detalles del producto en el archivo
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
            
            // Escribir el resumen del inventario
            writer.write("\nRESUMEN\n");
            writer.write("Total de productos diferentes: " + productos.size() + "\n");
            writer.write("Total de unidades en inventario: " + totalProductos + "\n");
            writer.write("Valor total del inventario: $" + valorTotalInventario + "\n");
            
            System.out.println("✅ Reporte CSV generado con éxito en " + rutaArchivo);
        } catch (IOException e) {
            // Manejar errores al escribir el archivo
            System.out.println("❌ Error al generar reporte CSV: " + e.getMessage());
        }
    }
}