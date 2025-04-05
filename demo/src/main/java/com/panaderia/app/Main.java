package com.panaderia.app;

import java.util.Scanner;

import com.panaderia.controlador.controladorProducto;
import com.panaderia.modelo.almacenProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.vista.vistaProducto;
import com.panaderia.vista.vistaReporte;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        vistaProducto vistaProducto = new vistaProducto();
        vistaReporte vistaReporte = new vistaReporte();
        almacenProductos almacen = new almacenProductos();
        generadorReporte reporte = new generadorReporte();

        controladorProducto controlador = new controladorProducto(vistaProducto, vistaReporte, almacen, reporte);
        controlador.cargarProductos();

        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Agregar producto");
            System.out.println("3. Actualizar producto por ID");
            System.out.println("4. Eliminar producto por ID");
            System.out.println("5. Filtrar productos por nombre");
            System.out.println("6. Generar reporte");
            System.out.println("7. Guardar productos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> controlador.listarProductos();
                case 2 -> controlador.agregarProducto();
                case 3 -> {
                    System.out.print("Ingrese el ID del producto a actualizar: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    controlador.actualizarProducto(id);
                }
                case 4 -> {
                    System.out.print("Ingrese el ID del producto a eliminar: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    controlador.eliminarProducto(id);
                }
                case 5 -> {
                    System.out.print("Ingrese nombre para filtrar: ");
                    String nombre = scanner.nextLine();
                    controlador.filtrarProductos("nombre", nombre);
                }
                case 6 -> controlador.generarReporte();
                case 7 -> almacen.guardarProductosEnArchivo();
                case 0 -> System.out.println("👋 Saliendo del programa...");
                default -> System.out.println("❌ Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}