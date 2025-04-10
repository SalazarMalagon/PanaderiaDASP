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

/**
 * Clase que representa un almacén de productos.
 * Gestiona la lista de productos en memoria y su persistencia en un archivo JSON.
 * Permite realizar operaciones CRUD y filtrar productos según diferentes criterios.
 */
public class almacenProductos {

    private List<producto> productos; // Lista de productos en memoria
    private final Gson gson; // Objeto Gson para serialización/deserialización

    /**
     * Constructor de la clase almacenProductos.
     * Configura los serializadores y deserializadores personalizados para los productos.
     * Inicializa la lista de productos.
     */
    public almacenProductos() {
        this.productos = new ArrayList<>();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        // Configuración del deserializador personalizado
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
        
        // Configuración del serializador personalizado
        JsonSerializer<producto> serializer = (src, typeOfSrc, context) -> {
            JsonObject jsonObject = new JsonObject();
            
            jsonObject.addProperty("idProducto", src.getIdProducto());
            jsonObject.addProperty("nombre", src.getNombre());
            jsonObject.addProperty("stock", src.getStock());
            jsonObject.addProperty("costo", src.getCosto());
            jsonObject.addProperty("precio", src.getPrecio());
            jsonObject.addProperty("tipo", src.getTipo());
            
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

    /**
     * Agrega un nuevo producto al almacén.
     * Asigna automáticamente un ID único al producto.
     * @param producto El producto a agregar.
     */
    public void agregarProducto(producto producto) {
        int nuevoId = productos.stream()
                       .mapToInt(p -> p.getIdProducto())
                       .max()
                       .orElse(0) + 1;
    
        producto.setIdProducto(nuevoId);
        productos.add(producto);
    }

    /**
     * Elimina un producto del almacén por su ID.
     * @param idProducto El ID del producto a eliminar.
     */
    public void eliminarProducto(int idProducto) {
        productos.removeIf(p -> p.getIdProducto() == idProducto);
    }

    /**
     * Actualiza un producto existente en el almacén.
     * @param productoActualizado El producto con los datos actualizados.
     */
    public void actualizarProducto(producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == productoActualizado.getIdProducto()) {
                productos.set(i, productoActualizado);
                return;
            }
        }
    }

    /**
     * Obtiene un producto por su ID.
     * @param idProducto El ID del producto a buscar.
     * @return El producto encontrado o `null` si no existe.
     */
    public producto obtenerProductoPorId(int idProducto) {
        return productos.stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los productos almacenados.
     * @return Una lista con todos los productos.
     */
    public List<producto> obtenerTodosProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Filtra los productos por nombre.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de productos que coinciden con el criterio.
     */
    public List<producto> filtrarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(productos);
        }
        
        String nombreLower = nombre.toLowerCase();
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    /**
     * Filtra los productos por un rango de precios.
     * @param minPrecio El precio mínimo.
     * @param maxPrecio El precio máximo.
     * @return Una lista de productos dentro del rango de precios.
     */
    public List<producto> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        return productos.stream()
                .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                .collect(Collectors.toList());
    }

    /**
     * Filtra los productos por un rango de cantidades en stock.
     * @param minCantidad La cantidad mínima.
     * @param maxCantidad La cantidad máxima.
     * @return Una lista de productos dentro del rango de cantidades.
     */
    public List<producto> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        return productos.stream()
                .filter(p -> p.getStock() >= minCantidad && p.getStock() <= maxCantidad)
                .collect(Collectors.toList());
    }

    /**
     * Carga los productos desde un archivo JSON.
     * Si el archivo no existe o está vacío, inicializa una lista vacía.
     */
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

    /**
     * Guarda los productos en un archivo JSON.
     */
    public void guardarProductosEnArchivo() {
        try (FileWriter writer = new FileWriter("demo/productos.json")) {
            gson.toJson(productos, writer);
            System.out.println("✅ Productos guardados en productos.json");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar productos: " + e.getMessage());
        }
    }

    /**
     * Busca un producto por su ID.
     * @param id El ID del producto a buscar.
     * @return El producto encontrado o `null` si no existe.
     */
    public producto buscarPorId(int id) {
        for (producto p : productos) {
            if (p.getIdProducto() == id) return p;
        }
        return null;
    }
}