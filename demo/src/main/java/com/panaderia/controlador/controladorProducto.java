package com.panaderia.controlador;

import com.panaderia.vista.vistaProducto;
import com.panaderia.vista.vistaReporte;
import com.panaderia.modelo.almacenProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.modelo.producto;
import java.util.List;

public class controladorProducto {

    private vistaProducto vistaProducto;
    private vistaReporte vistaReporte;
    private almacenProductos almacenProductos;
    private generadorReporte generadorReporte;
    
    public controladorProducto(vistaProducto vistaProducto, vistaReporte vistaReporte,
                               almacenProductos almacenProductos, generadorReporte generadorReporte) {
        this.vistaProducto = vistaProducto;
        this.vistaReporte = vistaReporte;
        this.almacenProductos = almacenProductos;
        this.generadorReporte = generadorReporte;
    }

    public void cargarProductos() {
        almacenProductos.cargarProductosDesdeArchivo();
    }

    public void agregarProducto() {
        producto nuevoProducto = vistaProducto.solicitarDatosProducto();
        almacenProductos.agregarProducto(nuevoProducto);
        almacenProductos.guardarProductosEnArchivo();
    }

    public void actualizarProducto(int idProducto) {
        producto actualizado = vistaProducto.solicitarDatosProducto();
        actualizado.setIdProducto(idProducto);
        almacenProductos.actualizarProducto(actualizado);
        almacenProductos.guardarProductosEnArchivo();
    }

    public void eliminarProducto(int idProducto) {
        almacenProductos.eliminarProducto(idProducto);
        almacenProductos.guardarProductosEnArchivo();
    }

    public void generarReporte() {
        List<producto> productos = almacenProductos.obtenerTodosProductos();
        generadorReporte.generarCSV(productos);
    }

    public void filtrarProductos(String criterio, String valor) {
        List<producto> resultado = switch (criterio.toLowerCase()) {
            case "nombre" -> almacenProductos.filtrarPorNombre(valor);
            case "precio" -> {
                String[] partes = valor.split("-");
                double min = Double.parseDouble(partes[0]);
                double max = Double.parseDouble(partes[1]);
                yield almacenProductos.filtrarPorPrecio(min, max);
            }
            case "cantidad" -> {
                String[] partes = valor.split("-");
                int min = Integer.parseInt(partes[0]);
                int max = Integer.parseInt(partes[1]);
                yield almacenProductos.filtrarPorCantidad(min, max);
            }
            default -> null;
        };

        if (resultado != null) {
            vistaProducto.mostrarListaProductos(resultado);
        } else {
            System.out.println("Criterio no válido.");
        }
    }

    public List<producto> obtenerProductos() {
        return almacenProductos.obtenerTodosProductos();
    }

    public void listarProductos() {
        List<producto> productos = almacenProductos.obtenerTodosProductos();
        vistaProducto.mostrarListaProductos(productos);
    }


}
