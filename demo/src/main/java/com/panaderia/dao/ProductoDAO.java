package com.panaderia.dao;

import com.panaderia.modelo.producto;
import java.util.List;

public interface ProductoDAO<T extends producto> {

    void guardar(T producto);

    boolean eliminar(int idProducto);
    
    boolean actualizar(T producto);
    
    T buscarPorId(int idProducto);
    
    List<T> obtenerTodos();

    List<T> filtrarPorNombre(String nombre);

    List<T> filtrarPorPrecio(double minPrecio, double maxPrecio);
    
    List<T> filtrarPorCantidad(int minCantidad, int maxCantidad);
    
    void guardarTodos();
    
    void cargarTodos();
}
