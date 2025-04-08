package com.panaderia.servicio;

import com.panaderia.dao.PanDAO;
import com.panaderia.dao.GalletaDAO;
import com.panaderia.modelo.producto;
import com.panaderia.modelo.pan;
import com.panaderia.modelo.galleta;

import java.util.ArrayList;
import java.util.List;

public class GestorProductos {
    private final PanDAO panDAO;
    private final GalletaDAO galletaDAO;
    
    public GestorProductos() {
        this.panDAO = new PanDAO();
        this.galletaDAO = new GalletaDAO();
        
        // Cargar todos los productos al inicializar
        panDAO.cargarTodos();
        galletaDAO.cargarTodos();
    }
    
    public void guardarProducto(producto producto) {
        if (producto instanceof pan) {
            panDAO.guardar((pan) producto);
        } else if (producto instanceof galleta) {
            galletaDAO.guardar((galleta) producto);
        }
    }
    
    public boolean eliminarProducto(int idProducto, String tipo) {
        if ("Pan".equals(tipo)) {
            return panDAO.eliminar(idProducto);
        } else if ("Galleta".equals(tipo)) {
            return galletaDAO.eliminar(idProducto);
        }
        return false;
    }
    
    public boolean eliminarProducto(int idProducto) {
        boolean eliminadoPan = panDAO.eliminar(idProducto);
        boolean eliminadaGalleta = galletaDAO.eliminar(idProducto);
        
        return eliminadoPan || eliminadaGalleta;
    }
    
    public boolean actualizarProducto(producto producto) {
        if (producto instanceof pan) {
            return panDAO.actualizar((pan) producto);
        } else if (producto instanceof galleta) {
            return galletaDAO.actualizar((galleta) producto);
        }
        return false;
    }

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
        
    public List<producto> obtenerTodosProductos() {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.obtenerTodos());
        productos.addAll(galletaDAO.obtenerTodos());
        return productos;
    }
    
    public List<producto> filtrarPorNombre(String nombre) {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.filtrarPorNombre(nombre));
        productos.addAll(galletaDAO.filtrarPorNombre(nombre));
        return productos;
    }
    
    public List<producto> filtrarPorPrecio(double minPrecio, double maxPrecio) {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.filtrarPorPrecio(minPrecio, maxPrecio));
        productos.addAll(galletaDAO.filtrarPorPrecio(minPrecio, maxPrecio));
        return productos;
    }
    
    public List<producto> filtrarPorCantidad(int minCantidad, int maxCantidad) {
        List<producto> productos = new ArrayList<>();
        productos.addAll(panDAO.filtrarPorCantidad(minCantidad, maxCantidad));
        productos.addAll(galletaDAO.filtrarPorCantidad(minCantidad, maxCantidad));
        return productos;
    }

    public void guardarTodos() {
        panDAO.guardarTodos();
        galletaDAO.guardarTodos();
    }

    public void cargarTodos() {
        panDAO.cargarTodos();
        galletaDAO.cargarTodos();
    }
    
}
