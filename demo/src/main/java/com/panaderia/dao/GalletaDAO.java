package com.panaderia.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;

import com.panaderia.modelo.galleta;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GalletaDAO implements ProductoDAO<galleta> {
    private List<galleta> galletas;
    private final String ARCHIVO_GALLETAS = "demo/galletas.json";
    private final Gson gson;
    
    public GalletaDAO() {
        this.galletas = new ArrayList<>();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        // Configurarion serializador/deserializador
        JsonDeserializer<galleta> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            
            int id = jsonObject.get("idProducto").getAsInt();
            String nombre = jsonObject.get("nombre").getAsString();
            int stock = jsonObject.get("stock").getAsInt();
            double costo = jsonObject.get("costo").getAsDouble();
            double precio = jsonObject.get("precio").getAsDouble();
            boolean tieneChispas = jsonObject.has("tieneChispas") ? 
                    jsonObject.get("tieneChispas").getAsBoolean() : false;
            
            return new galleta(id, nombre, stock, costo, precio, tieneChispas);
        };
        
        JsonSerializer<galleta> serializer = (src, typeOfSrc, context) -> {
            JsonObject jsonObject = new JsonObject();
            
            jsonObject.addProperty("idProducto", src.getIdProducto());
            jsonObject.addProperty("nombre", src.getNombre());
            jsonObject.addProperty("stock", src.getStock());
            jsonObject.addProperty("costo", src.getCosto());
            jsonObject.addProperty("precio", src.getPrecio());
            jsonObject.addProperty("tipo", src.getTipo());
            jsonObject.addProperty("tieneChispas", src.getTieneChispas());
            
            return jsonObject;
        };
        
        gsonBuilder.registerTypeAdapter(galleta.class, deserializer);
        gsonBuilder.registerTypeAdapter(galleta.class, serializer);
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        
        cargarTodos();
    }

    @Override
    public void guardar(galleta producto) {
        if (producto.getIdProducto() == 0) {
            int nuevoId = galletas.stream()
                           .mapToInt(p -> p.getIdProducto())
                           .max()
                           .orElse(0) + 1;
            producto.setIdProducto(nuevoId);
        }
        
        galletas.add(producto);
        guardarTodos();
    }

    @Override
    public boolean eliminar(int idProducto) {
        boolean eliminado = galletas.removeIf(p -> p.getIdProducto() == idProducto);
        if (eliminado) {
            guardarTodos();
        }
        return eliminado;
    }

    @Override
    public boolean actualizar(galleta producto) {
        for (int i = 0; i < galletas.size(); i++) {
            if (galletas.get(i).getIdProducto() == producto.getIdProducto()) {
                galletas.set(i, producto);
                guardarTodos();
                return true;
            }
        }
        return false;
    }

    @Override
    public galleta buscarPorId(int idProducto) {
        return galletas.stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<galleta> obtenerTodos() {
        return new ArrayList<>(galletas);
    }

    @Override
    public List<galleta> filtrarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(galletas);
        }
        
        String nombreLower = nombre.toLowerCase();
        return galletas.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    @Override
    public List<galleta> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        return galletas.stream()
                .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                .collect(Collectors.toList());
    }

    @Override
    public List<galleta> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        return galletas.stream()
                .filter(p -> p.getStock() >= minCantidad && p.getStock() <= maxCantidad)
                .collect(Collectors.toList());
    }
    
    public List<galleta> filtrarPorChispas(boolean tieneChispas) {
        return galletas.stream()
                .filter(p -> p.getTieneChispas() == tieneChispas)
                .collect(Collectors.toList());
    }

    @Override
    public void guardarTodos() {
        try (FileWriter writer = new FileWriter(ARCHIVO_GALLETAS)) {
            gson.toJson(galletas, writer);
            System.out.println("✅ Galletas guardadas en " + ARCHIVO_GALLETAS);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar galletas: " + e.getMessage());
        }
    }

    @Override
    public void cargarTodos() {
        try (FileReader reader = new FileReader(ARCHIVO_GALLETAS)) {
            galletas = gson.fromJson(reader, new TypeToken<List<galleta>>(){}.getType());
            if (galletas == null) {
                galletas = new ArrayList<>();
            }
            System.out.println("✅ Galletas cargadas desde " + ARCHIVO_GALLETAS);
        } catch (IOException e) {
            System.out.println("❌ Error al cargar galletas: " + e.getMessage());
            galletas = new ArrayList<>();
        }
    }
}
