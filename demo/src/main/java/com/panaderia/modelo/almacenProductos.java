package com.panaderia.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class almacenProductos {

    private List<producto> productos;
    private final Gson gson;

    public almacenProductos() {
        this.productos = new ArrayList<>();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        JsonDeserializer<producto> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            
            int id = jsonObject.get("idProducto").getAsInt();
            String nombre = jsonObject.get("nombre").getAsString();
            int stock = jsonObject.get("stock").getAsInt();
            double costo = jsonObject.get("costo").getAsDouble();
            double precio = jsonObject.get("precio").getAsDouble();
            
            
            if (jsonObject.has("tipo")) {
                String tipo = jsonObject.get("tipo").getAsString();
                
                if ("Pan".equals(tipo)) {
                    boolean tieneQueso = jsonObject.has("tieneQueso") ? 
                            jsonObject.get("tieneQueso").getAsBoolean() : false;
                    return new pan(id, nombre, stock, costo, precio, tieneQueso);
                } 
                else if ("Galleta".equals(tipo)) {
                    boolean tieneChispas = jsonObject.has("tieneChispas") ? 
                            jsonObject.get("tieneChispas").getAsBoolean() : false;
                    return new galleta(id, nombre, stock, costo, precio, tieneChispas);
                }
                producto p = new producto(id, nombre, stock, costo, precio);
                p.setTipo(tipo);
                return p;
            }
            
            return new producto(id, nombre, stock, costo, precio);
        };
        
        JsonSerializer<producto> serializer = (src, typeOfSrc, context) -> {
            JsonObject jsonObject = new JsonObject();
            
            jsonObject.addProperty("idProducto", src.getIdProducto());
            jsonObject.addProperty("nombre", src.getNombre());
            jsonObject.addProperty("stock", src.getStock());
            jsonObject.addProperty("costo", src.getCosto());
            jsonObject.addProperty("precio", src.getPrecio());
            jsonObject.addProperty("tipo", src.getTipo());  // Serializa el tipo directamente
            
            if (src instanceof pan) {
                jsonObject.addProperty("tieneQueso", ((pan) src).getTieneQueso());
            } else if (src instanceof galleta) {
                jsonObject.addProperty("tieneChispas", ((galleta) src).getTieneChispas());
            }
            
            return jsonObject;
        };
        
        gsonBuilder.registerTypeAdapter(producto.class, deserializer);
        gsonBuilder.registerTypeAdapter(producto.class, serializer);
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public void agregarProducto(producto producto) {
        int nuevoId = productos.stream()
                       .mapToInt(p -> p.getIdProducto())
                       .max()
                       .orElse(0) + 1;
    
        producto.setIdProducto(nuevoId);
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
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(productos);
        }
        
        String nombreLower = nombre.toLowerCase();
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreLower))
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
        try (FileReader reader = new FileReader("demo/productos.json")) {
            productos = gson.fromJson(reader, new TypeToken<List<producto>>(){}.getType());
            if (productos == null) {
                productos = new ArrayList<>();
            }
            System.out.println("✅ Productos cargados desde productos.json");
        } catch (IOException e) {
            System.out.println("❌ Error al cargar productos: " + e.getMessage());
            productos = new ArrayList<>();
        }
    }

    public void guardarProductosEnArchivo() {
        try (FileWriter writer = new FileWriter("demo/productos.json")) {
            gson.toJson(productos, writer);
            System.out.println("✅ Productos guardados en productos.json");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar productos: " + e.getMessage());
        }
    }

    public producto buscarPorId(int id) {
        for (producto p : productos) {
            if (p.getIdProducto() == id) return p;
        }
        return null;
    }
}