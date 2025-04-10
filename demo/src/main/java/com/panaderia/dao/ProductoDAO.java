package com.panaderia.dao;

import com.panaderia.modelo.producto;
import java.util.List;

/**
 * Interfaz genérica para el acceso a datos (DAO) de productos.
 * Define las operaciones CRUD y métodos adicionales para la gestión de productos.
 * @param <T> Tipo genérico que extiende de la clase `producto`.
 */
public interface ProductoDAO<T extends producto> {

    /**
     * Guarda un producto en la base de datos o lista en memoria.
     * @param producto El producto a guardar.
     */
    void guardar(T producto);

    /**
     * Elimina un producto por su ID.
     * @param idProducto El ID del producto a eliminar.
     * @return `true` si el producto fue eliminado correctamente, `false` en caso contrario.
     */
    boolean eliminar(int idProducto);
    
    /**
     * Actualiza un producto existente.
     * @param producto El producto con los datos actualizados.
     * @return `true` si el producto fue actualizado correctamente, `false` en caso contrario.
     */
    boolean actualizar(T producto);
    
    /**
     * Busca un producto por su ID.
     * @param idProducto El ID del producto a buscar.
     * @return El producto encontrado o `null` si no existe.
     */
    T buscarPorId(int idProducto);
    
    /**
     * Obtiene todos los productos almacenados.
     * @return Una lista con todos los productos.
     */
    List<T> obtenerTodos();

    /**
     * Filtra los productos por nombre.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de productos que coinciden con el criterio.
     */
    List<T> filtrarPorNombre(String nombre);

    /**
     * Filtra los productos por un rango de precios.
     * @param minPrecio El precio mínimo.
     * @param maxPrecio El precio máximo.
     * @return Una lista de productos dentro del rango de precios.
     */
    List<T> filtrarPorPrecio(double minPrecio, double maxPrecio);
    
    /**
     * Filtra los productos por un rango de cantidades en stock.
     * @param minCantidad La cantidad mínima.
     * @param maxCantidad La cantidad máxima.
     * @return Una lista de productos dentro del rango de cantidades.
     */
    List<T> filtrarPorCantidad(int minCantidad, int maxCantidad);
    
    /**
     * Guarda todos los productos en el almacenamiento persistente.
     */
    void guardarTodos();
    
    /**
     * Carga todos los productos desde el almacenamiento persistente.
     */
    void cargarTodos();
}