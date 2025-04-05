package com.panaderia.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; //Permite transformar, filtrar y recolectar elementos de una lista usando Streams.
import java.io.FileWriter;//Sirve para escribir datos en archivos de texto
import java.io.FileReader;//Sirve para leer archivos de texto
import java.io.IOException;
import com.google.gson.Gson;//permite convertir objetos Java a JSON
import com.google.gson.reflect.TypeToken;

public class almacenProductos {

    private List<producto> productos;

    public almacenProductos() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(int idProducto) {
        productos.removeIf(p -> p.getIdProducto() == idProducto);
    }

    public void actualizarProducto(producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == productoActualizado.getIdProducto()) {
                productos.set(i, productoActualizado);
                return;
            }
        }
    }

    public producto obtenerProductoPorId(int idProducto) {
        return productos.stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);
    }

    public List<producto> obtenerTodosProductos() {
        return new ArrayList<>(productos);
    }

    public List<producto> filtrarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public List<producto> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        return productos.stream()
                .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                .collect(Collectors.toList());
    }

    public List<producto> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        return productos.stream()
                .filter(p -> p.getStock() >= minCantidad && p.getStock() <= maxCantidad)
                .collect(Collectors.toList());
    }

    public void cargarProductosDesdeArchivo() {
        try (FileReader reader = new FileReader("productos.json")) {
            Gson gson = new Gson();
            productos = gson.fromJson(reader, new TypeToken<List<producto>>(){}.getType());
            if (productos == null) {
                productos = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }

    public void guardarProductosEnArchivo() {
        try (FileWriter writer = new FileWriter("productos.json")) {
            Gson gson = new Gson();
            gson.toJson(productos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }
}
