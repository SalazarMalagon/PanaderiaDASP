package com.panaderia.vista;

import com.panaderia.modelo.producto;

import java.util.List;
import java.util.Scanner;
public class vistaProducto {

    private Scanner scanner;

    public vistaProducto() {
        scanner = new Scanner(System.in);
    }

    public producto solicitarDatosProducto() {
        System.out.println("Ingrese los datos del producto:");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());

        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.print("Costo: ");
        double costo = Double.parseDouble(scanner.nextLine());

        return new producto(0, nombre, stock, precio, costo); // ID se asignará o actualizará desde el controlador
    }

    public void mostrarListaProductos(List<producto> productos) {
        System.out.println("----- Lista de Productos -----");
        for (producto p : productos) {
            System.out.println("ID: " + p.getIdProducto());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Stock: " + p.getStock());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Costo: " + p.getCosto());
            System.out.println("------------------------------");
        }
    }

}
