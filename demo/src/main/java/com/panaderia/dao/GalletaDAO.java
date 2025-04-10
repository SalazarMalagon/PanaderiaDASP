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

/**
 * Clase que implementa el acceso a datos (DAO) para la entidad `galleta`.
 * Permite realizar operaciones CRUD y gestionar la persistencia de datos en un archivo JSON.
 */
public class GalletaDAO implements ProductoDAO<galleta> {
    private List<galleta> galletas; // Lista de galletas en memoria
    private final String ARCHIVO_GALLETAS = "demo/galletas.json"; // Ruta del archivo JSON
    private final Gson gson; // Objeto Gson para serialización/deserialización

    /**
     * Constructor de la clase GalletaDAO.
     * Configura los serializadores y deserializadores personalizados para la clase `galleta`.
     * Carga los datos desde el archivo JSON al inicializar.
     */
    public GalletaDAO() {
        this.galletas = new ArrayList<>();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        // Configuración del deserializador personalizado
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
        
        // Configuración del serializador personalizado
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
        
        // Cargar los datos desde el archivo JSON
        cargarTodos();
    }

    /**
     * Guarda una nueva galleta en la lista y persiste los datos en el archivo JSON.
     * Si el producto no tiene ID, se genera uno automáticamente.
     * @param producto La galleta a guardar.
     */
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

    /**
     * Elimina una galleta de la lista por su ID y persiste los cambios.
     * @param idProducto El ID de la galleta a eliminar.
     * @return `true` si se eliminó correctamente, `false` en caso contrario.
     */
    @Override
    public boolean eliminar(int idProducto) {
        boolean eliminado = galletas.removeIf(p -> p.getIdProducto() == idProducto);
        if (eliminado) {
            guardarTodos();
        }
        return eliminado;
    }

    /**
     * Actualiza una galleta existente en la lista y persiste los cambios.
     * @param producto La galleta con los datos actualizados.
     * @return `true` si se actualizó correctamente, `false` en caso contrario.
     */
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

    /**
     * Busca una galleta por su ID.
     * @param idProducto El ID de la galleta a buscar.
     * @return La galleta encontrada o `null` si no existe.
     */
    @Override
    public galleta buscarPorId(int idProducto) {
        return galletas.stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todas las galletas almacenadas.
     * @return Una lista con todas las galletas.
     */
    @Override
    public List<galleta> obtenerTodos() {
        return new ArrayList<>(galletas);
    }

    /**
     * Filtra las galletas por nombre.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de galletas que coinciden con el criterio.
     */
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

    /**
     * Filtra las galletas por un rango de precios.
     * @param minPrecio El precio mínimo.
     * @param maxPrecio El precio máximo.
     * @return Una lista de galletas dentro del rango de precios.
     */
    @Override
    public List<galleta> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        return galletas.stream()
                .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                .collect(Collectors.toList());
    }

    /**
     * Filtra las galletas por un rango de cantidades en stock.
     * @param minCantidad La cantidad mínima.
     * @param maxCantidad La cantidad máxima.
     * @return Una lista de galletas dentro del rango de cantidades.
     */
    @Override
    public List<galleta> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        return galletas.stream()
                .filter(p -> p.getStock() >= minCantidad && p.getStock() <= maxCantidad)
                .collect(Collectors.toList());
    }

    /**
     * Filtra las galletas según si tienen chispas o no.
     * @param tieneChispas `true` para buscar galletas con chispas, `false` para sin chispas.
     * @return Una lista de galletas que cumplen con el criterio.
     */
    public List<galleta> filtrarPorChispas(boolean tieneChispas) {
        return galletas.stream()
                .filter(p -> p.getTieneChispas() == tieneChispas)
                .collect(Collectors.toList());
    }

    /**
     * Guarda todas las galletas en el archivo JSON.
     */
    @Override
    public void guardarTodos() {
        try (FileWriter writer = new FileWriter(ARCHIVO_GALLETAS)) {
            gson.toJson(galletas, writer);
            System.out.println("✅ Galletas guardadas en " + ARCHIVO_GALLETAS);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar galletas: " + e.getMessage());
        }
    }

    /**
     * Carga todas las galletas desde el archivo JSON.
     * Si el archivo no existe o está vacío, inicializa una lista vacía.
     */
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