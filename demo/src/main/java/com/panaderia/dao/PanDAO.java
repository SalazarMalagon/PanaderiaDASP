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

/**
 * Clase que implementa el acceso a datos (DAO) para la entidad `pan`.
 * Permite realizar operaciones CRUD y gestionar la persistencia de datos en un archivo JSON.
 */
public class PanDAO implements ProductoDAO<pan> {
    private List<pan> panes; // Lista de panes en memoria
    private final String ARCHIVO_PANES = "demo/panes.json"; // Ruta del archivo JSON
    private final Gson gson; // Objeto Gson para serialización/deserialización

    /**
     * Constructor de la clase PanDAO.
     * Configura los serializadores y deserializadores personalizados para la clase `pan`.
     * Carga los datos desde el archivo JSON al inicializar.
     */
    public PanDAO() {
        this.panes = new ArrayList<>();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        // Configuración del deserializador personalizado
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
        
        // Configuración del serializador personalizado
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
        
        // Cargar los datos desde el archivo JSON
        cargarTodos();
    }

    /**
     * Guarda un nuevo pan en la lista y persiste los datos en el archivo JSON.
     * Si el producto no tiene ID, se genera uno automáticamente.
     * @param producto El pan a guardar.
     */
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

    /**
     * Elimina un pan de la lista por su ID y persiste los cambios.
     * @param idProducto El ID del pan a eliminar.
     * @return `true` si se eliminó correctamente, `false` en caso contrario.
     */
    @Override
    public boolean eliminar(int idProducto) {
        boolean eliminado = panes.removeIf(p -> p.getIdProducto() == idProducto);
        if (eliminado) {
            guardarTodos();
        }
        return eliminado;
    }

    /**
     * Actualiza un pan existente en la lista y persiste los cambios.
     * @param producto El pan con los datos actualizados.
     * @return `true` si se actualizó correctamente, `false` en caso contrario.
     */
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

    /**
     * Busca un pan por su ID.
     * @param idProducto El ID del pan a buscar.
     * @return El pan encontrado o `null` si no existe.
     */
    @Override
    public pan buscarPorId(int idProducto) {
        return panes.stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los panes almacenados.
     * @return Una lista con todos los panes.
     */
    @Override
    public List<pan> obtenerTodos() {
        return new ArrayList<>(panes);
    }

    /**
     * Filtra los panes por nombre.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de panes que coinciden con el criterio.
     */
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

    /**
     * Filtra los panes por un rango de precios.
     * @param minPrecio El precio mínimo.
     * @param maxPrecio El precio máximo.
     * @return Una lista de panes dentro del rango de precios.
     */
    @Override
    public List<pan> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        return panes.stream()
                .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                .collect(Collectors.toList());
    }

    /**
     * Filtra los panes por un rango de cantidades en stock.
     * @param minCantidad La cantidad mínima.
     * @param maxCantidad La cantidad máxima.
     * @return Una lista de panes dentro del rango de cantidades.
     */
    @Override
    public List<pan> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        return panes.stream()
                .filter(p -> p.getStock() >= minCantidad && p.getStock() <= maxCantidad)
                .collect(Collectors.toList());
    }

    /**
     * Filtra los panes según si tienen queso o no.
     * @param tieneQueso `true` para buscar panes con queso, `false` para sin queso.
     * @return Una lista de panes que cumplen con el criterio.
     */
    public List<pan> filtrarPorQueso(boolean tieneQueso) {
        return panes.stream()
                .filter(p -> p.getTieneQueso() == tieneQueso)
                .collect(Collectors.toList());
    }

    /**
     * Guarda todos los panes en el archivo JSON.
     */
    @Override
    public void guardarTodos() {
        try (FileWriter writer = new FileWriter(ARCHIVO_PANES)) {
            gson.toJson(panes, writer);
            System.out.println("✅ Panes guardados en " + ARCHIVO_PANES);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar panes: " + e.getMessage());
        }
    }

    /**
     * Carga todos los panes desde el archivo JSON.
     * Si el archivo no existe o está vacío, inicializa una lista vacía.
     */
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