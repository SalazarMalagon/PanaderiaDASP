package com.panaderia.servicio;

import com.panaderia.dao.PanDAO;
import com.panaderia.dao.GalletaDAO;
import com.panaderia.modelo.producto;
import com.panaderia.modelo.pan;
import com.panaderia.modelo.galleta;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones relacionadas con los productos de la panadería.
 * Actúa como intermediario entre las capas de datos (DAOs) y la lógica de negocio.
 */
public class GestorProductos {
    private final PanDAO panDAO; // DAO para gestionar los datos de los panes
    private final GalletaDAO galletaDAO; // DAO para gestionar los datos de las galletas
    
    /**
     * Constructor de la clase GestorProductos.
     * Inicializa los DAOs y carga todos los productos desde los archivos JSON.
     */
    public GestorProductos() {
        this.panDAO = new PanDAO();
        this.galletaDAO = new GalletaDAO();
        
        // Cargar todos los productos al inicializar
        panDAO.cargarTodos();
        galletaDAO.cargarTodos();
    }
    
    /**
     * Guarda un producto en el DAO correspondiente según su tipo.
     * @param producto El producto a guardar.
     */
    public void guardarProducto(producto producto) {
        if (producto instanceof pan) {
            panDAO.guardar((pan) producto);
        } else if (producto instanceof galleta) {
            galletaDAO.guardar((galleta) producto);
        }
    }
    
    /**
     * Elimina un producto por su ID y tipo.
     * @param idProducto El ID del producto a eliminar.
     * @param tipo El tipo del producto ("Pan" o "Galleta").
     * @return `true` si el producto fue eliminado, `false` en caso contrario.
     */
    public boolean eliminarProducto(int idProducto, String tipo) {
        if ("Pan".equals(tipo)) {
            return panDAO.eliminar(idProducto);
        } else if ("Galleta".equals(tipo)) {
            return galletaDAO.eliminar(idProducto);
        }
        return false;
    }
    
    /**
     * Elimina un producto por su ID, buscando en ambos DAOs.
     * @param idProducto El ID del producto a eliminar.
     * @return `true` si el producto fue eliminado, `false` en caso contrario.
     */
    public boolean eliminarProducto(int idProducto) {
        boolean eliminadoPan = panDAO.eliminar(idProducto);
        boolean eliminadaGalleta = galletaDAO.eliminar(idProducto);
        
        return eliminadoPan || eliminadaGalleta;
    }
    
    /**
     * Actualiza un producto en el DAO correspondiente según su tipo.
     * @param producto El producto con los datos actualizados.
     * @return `true` si el producto fue actualizado, `false` en caso contrario.
     */
    public boolean actualizarProducto(producto producto) {
        if (producto instanceof pan) {
            return panDAO.actualizar((pan) producto);
        } else if (producto instanceof galleta) {
            return galletaDAO.actualizar((galleta) producto);
        }
        return false;
    }

    /**
     * Busca un producto por su ID en ambos DAOs.
     * @param idProducto El ID del producto a buscar.
     * @return El producto encontrado o `null` si no existe.
     */
    public producto buscarPorId(int idProducto) {
        pan pan = panDAO.buscarPorId(idProducto);
        if (pan != null) {
            return pan;
        }
        
        galleta galleta = galletaDAO.buscarPorId(idProducto);
        if (galleta != null) {
            return galleta;
        }
        
        return null;
    }
        
    /**
     * Obtiene todos los productos almacenados en ambos DAOs.
     * @return Una lista con todos los productos.
     */
    public List<producto> obtenerTodosProductos() {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.obtenerTodos());
        productos.addAll(galletaDAO.obtenerTodos());
        return productos;
    }
    
    /**
     * Filtra los productos por nombre en ambos DAOs.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de productos que coinciden con el criterio.
     */
    public List<producto> filtrarPorNombre(String nombre) {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.filtrarPorNombre(nombre));
        productos.addAll(galletaDAO.filtrarPorNombre(nombre));
        return productos;
    }
    
    /**
     * Filtra los productos por un rango de precios en ambos DAOs.
     * @param minPrecio El precio mínimo.
     * @param maxPrecio El precio máximo.
     * @return Una lista de productos dentro del rango de precios.
     */
    public List<producto> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.filtrarPorPrecio(minPrecio, maxPrecio));
        productos.addAll(galletaDAO.filtrarPorPrecio(minPrecio, maxPrecio));
        return productos;
    }
    
    /**
     * Filtra los productos por un rango de cantidades en stock en ambos DAOs.
     * @param minCantidad La cantidad mínima.
     * @param maxCantidad La cantidad máxima.
     * @return Una lista de productos dentro del rango de cantidades.
     */
    public List<producto> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.filtrarPorCantidad(minCantidad, maxCantidad));
        productos.addAll(galletaDAO.filtrarPorCantidad(minCantidad, maxCantidad));
        return productos;
    }

    /**
     * Guarda todos los productos en sus respectivos DAOs.
     */
    public void guardarTodos() {
        panDAO.guardarTodos();
        galletaDAO.guardarTodos();
    }

    /**
     * Carga todos los productos desde sus respectivos DAOs.
     */
    public void cargarTodos() {
        panDAO.cargarTodos();
        galletaDAO.cargarTodos();
    }
}