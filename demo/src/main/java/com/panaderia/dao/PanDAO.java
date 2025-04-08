package com.panaderia.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;

import com.panaderia.modelo.pan;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanDAO implements ProductoDAO<pan> {
    private List<pan> panes;
    private final String ARCHIVO_PANES = "demo/panes.json";
    private final Gson gson;
    
    public PanDAO() {
        this.panes = new ArrayList<>();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        // Configurarcion serializador/deserializador 
        JsonDeserializer<pan> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            
            int id = jsonObject.get("idProducto").getAsInt();
            String nombre = jsonObject.get("nombre").getAsString();
            int stock = jsonObject.get("stock").getAsInt();
            double costo = jsonObject.get("costo").getAsDouble();
            double precio = jsonObject.get("precio").getAsDouble();
            boolean tieneQueso = jsonObject.has("tieneQueso") ? 
                    jsonObject.get("tieneQueso").getAsBoolean() : false;
            
            return new pan(id, nombre, stock, costo, precio, tieneQueso);
        };
        
        JsonSerializer<pan> serializer = (src, typeOfSrc, context) -> {
            JsonObject jsonObject = new JsonObject();
            
            jsonObject.addProperty("idProducto", src.getIdProducto());
            jsonObject.addProperty("nombre", src.getNombre());
            jsonObject.addProperty("stock", src.getStock());
            jsonObject.addProperty("costo", src.getCosto());
            jsonObject.addProperty("precio", src.getPrecio());
            jsonObject.addProperty("tipo", src.getTipo());
            jsonObject.addProperty("tieneQueso", src.getTieneQueso());
            
            return jsonObject;
        };
        
        gsonBuilder.registerTypeAdapter(pan.class, deserializer);
        gsonBuilder.registerTypeAdapter(pan.class, serializer);
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        
        cargarTodos();
    }

    @Override
    public void guardar(pan producto) {
        if (producto.getIdProducto() == 0) {
            int nuevoId = panes.stream()
                           .mapToInt(p -> p.getIdProducto())
                           .max()
                           .orElse(0) + 1;
            producto.setIdProducto(nuevoId);
        }
        
        panes.add(producto);
        guardarTodos();
    }

    @Override
    public boolean eliminar(int idProducto) {
        boolean eliminado = panes.removeIf(p -> p.getIdProducto() == idProducto);
        if (eliminado) {
            guardarTodos();
        }
        return eliminado;
    }

    @Override
    public boolean actualizar(pan producto) {
        for (int i = 0; i < panes.size(); i++) {
            if (panes.get(i).getIdProducto() == producto.getIdProducto()) {
                panes.set(i, producto);
                guardarTodos();
                return true;
            }
        }
        return false;
    }

    @Override
    public pan buscarPorId(int idProducto) {
        return panes.stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<pan> obtenerTodos() {
        return new ArrayList<>(panes);
    }

    @Override
    public List<pan> filtrarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(panes);
        }
        
        String nombreLower = nombre.toLowerCase();
        return panes.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    @Override
    public List<pan> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        return panes.stream()
                .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                .collect(Collectors.toList());
    }

    @Override
    public List<pan> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        return panes.stream()
                .filter(p -> p.getStock() >= minCantidad && p.getStock() <= maxCantidad)
                .collect(Collectors.toList());
    }
    
    public List<pan> filtrarPorQueso(boolean tieneQueso) {
        return panes.stream()
                .filter(p -> p.getTieneQueso() == tieneQueso)
                .collect(Collectors.toList());
    }

    @Override
    public void guardarTodos() {
        try (FileWriter writer = new FileWriter(ARCHIVO_PANES)) {
            gson.toJson(panes, writer);
            System.out.println("✅ Panes guardados en " + ARCHIVO_PANES);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar panes: " + e.getMessage());
        }
    }

    @Override
    public void cargarTodos() {
        try (FileReader reader = new FileReader(ARCHIVO_PANES)) {
            panes = gson.fromJson(reader, new TypeToken<List<pan>>(){}.getType());
            if (panes == null) {
                panes = new ArrayList<>();
            }
            System.out.println("✅ Panes cargados desde " + ARCHIVO_PANES);
        } catch (IOException e) {
            System.out.println("❌ Error al cargar panes: " + e.getMessage());
            panes = new ArrayList<>();
        }
    }
}
