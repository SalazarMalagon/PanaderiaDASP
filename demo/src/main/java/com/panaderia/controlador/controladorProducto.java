package com.panaderia.controlador;

import com.panaderia.vista.vistaProductoGUI;
import com.panaderia.vista.vistaReporte;
import com.panaderia.modelo.almacenProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.modelo.producto;
import java.util.List;

import javax.swing.JOptionPane;

public class controladorProducto {

    private vistaProductoGUI vistaProductoGUI;
    private vistaReporte vistaReporte;
    private almacenProductos almacenProductos;
    private generadorReporte generadorReporte;
    
    public controladorProducto(vistaProductoGUI vistaProductoGUI, vistaReporte vistaReporte,
                               almacenProductos almacenProductos, generadorReporte generadorReporte) {
        this.vistaProductoGUI = vistaProductoGUI;
        this.vistaReporte = vistaReporte;
        this.almacenProductos = almacenProductos;
        this.generadorReporte = generadorReporte;
    }

    public void cargarProductos() {
        almacenProductos.cargarProductosDesdeArchivo();
    }

    public void agregarProducto() {
        producto nuevoProducto = vistaProductoGUI.solicitarDatosProducto();
        almacenProductos.agregarProducto(nuevoProducto);
        almacenProductos.guardarProductosEnArchivo();
    }

    public void actualizarProducto() {
        int idProducto = vistaProductoGUI.obtenerIdProductoSeleccionado();
        if (idProducto == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        producto productoActualizado = vistaProductoGUI.solicitarDatosProducto();
        productoActualizado.setIdProducto(idProducto);
        almacenProductos.actualizarProducto(productoActualizado);
        almacenProductos.guardarProductosEnArchivo();
        listarProductos(); // Actualizar la tabla
    }

    public void eliminarProducto() {
        int idProducto = vistaProductoGUI.obtenerIdProductoSeleccionado();
        if (idProducto == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            almacenProductos.eliminarProducto(idProducto);
            almacenProductos.guardarProductosEnArchivo();
            listarProductos(); // Actualizar la tabla
        }
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
            vistaProductoGUI.mostrarListaProductos(resultado);
        } else {
            System.out.println("Criterio no válido.");
        }
    }

    public List<producto> obtenerProductos() {
        return almacenProductos.obtenerTodosProductos();
    }

    public void listarProductos() {
        List<producto> productos = almacenProductos.obtenerTodosProductos();
        vistaProductoGUI.mostrarListaProductos(productos);
    }

    public void inicializar() {
        vistaProductoGUI.addAgregarListener(e -> {
            agregarProducto();
            listarProductos();
        });
    
        vistaProductoGUI.addActualizarListener(e -> actualizarProducto());
        vistaProductoGUI.addEliminarListener(e -> eliminarProducto());
    
        vistaProductoGUI.addBuscarListener(e -> {
            String texto = vistaProductoGUI.getTextoBusqueda().trim();
            if (texto.isEmpty()) {
                listarProductos(); // Mostrar todos si no hay filtro
                return;
            }
    
            try {
                if (texto.matches("\\d+")) { // Si es solo números, busca por ID
                    int id = Integer.parseInt(texto);
                    producto p = almacenProductos.buscarPorId(id);
                    if (p != null) {
                        vistaProductoGUI.mostrarListaProductos(List.of(p));
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró producto con ID: " + id);
                    }
                } else { // Si es texto, busca por nombre
                    filtrarProductos("nombre", texto);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al filtrar: " + ex.getMessage());
            }
        });
    }


}
