package com.panaderia.controlador;

import com.panaderia.vista.vistaProductoGUI;
import com.panaderia.vista.vistaReporte;

import scorex.util.ArrayList;

import com.panaderia.modelo.almacenProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.modelo.producto;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.util.List;

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
        try {
            producto nuevoProducto = vistaProductoGUI.solicitarDatosProducto();
            if (nuevoProducto != null) {
                nuevoProducto.validarCosto();
                nuevoProducto.validarPrecioVenta();
                nuevoProducto.validarStockNoNegativo();
                
                almacenProductos.agregarProducto(nuevoProducto);
                almacenProductos.guardarProductosEnArchivo();
                listarProductos();
                JOptionPane.showMessageDialog(null, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarProducto() {
        try {
            int idProducto = vistaProductoGUI.obtenerIdProductoSeleccionado();
            if (idProducto == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            producto productoActualizado = vistaProductoGUI.solicitarDatosProducto();
            if (productoActualizado != null) {
                productoActualizado.setIdProducto(idProducto);
                
                productoActualizado.validarCosto();
                productoActualizado.validarPrecioVenta();
                productoActualizado.validarStockNoNegativo();
                
                almacenProductos.actualizarProducto(productoActualizado);
                almacenProductos.guardarProductosEnArchivo();
                listarProductos(); 
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarProducto() {
        int idProducto = vistaProductoGUI.obtenerIdProductoSeleccionado();
        if (idProducto == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de que desea eliminar este producto?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            almacenProductos.eliminarProducto(idProducto);
            almacenProductos.guardarProductosEnArchivo();
            listarProductos();
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void generarReporte() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte CSV");
        fileChooser.setSelectedFile(new java.io.File("reporte_productos.csv"));
        
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                List<producto> productos = almacenProductos.obtenerTodosProductos();
                generadorReporte.generarCSV(productos, rutaArchivo);
                vistaReporte.mostrarMensajeReporteGenerado();
                JOptionPane.showMessageDialog(null, 
                    "Reporte generado con éxito en: " + rutaArchivo, 
                    "Reporte CSV", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                vistaReporte.mostrarMensajeErrorReporte();
                JOptionPane.showMessageDialog(null, 
                    "Error al generar reporte: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void filtrarProductos(String criterio, String valorMin, String valorMax) {
        List<producto> resultado = null;
        
        try {
            switch (criterio.toLowerCase()) {
                case "nombre":
                    resultado = almacenProductos.filtrarPorNombre(valorMin);
                    break;
                case "precio":
                    double min = Double.parseDouble(valorMin);
                    double max = Double.parseDouble(valorMax);
                    resultado = almacenProductos.filtrarPorPrecio(min, max);
                    break;
                case "cantidad":
                    int minCant = Integer.parseInt(valorMin);
                    int maxCant = Integer.parseInt(valorMax);
                    resultado = almacenProductos.filtrarPorCantidad(minCant, maxCant);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, 
                        "Criterio no válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            if (resultado != null && !resultado.isEmpty()) {
                vistaProductoGUI.mostrarProductosFiltrados(resultado);
                JOptionPane.showMessageDialog(null, 
                    "Se encontraron " + resultado.size() + " productos.", 
                    "Filtro aplicado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                vistaProductoGUI.mostrarProductosFiltrados(new ArrayList<>());
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron productos con ese criterio.", 
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Por favor ingrese valores numéricos válidos para los rangos.", 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al filtrar: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
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
        vistaProductoGUI.addAgregarListener(e -> agregarProducto());
        vistaProductoGUI.addActualizarListener(e -> actualizarProducto());
        vistaProductoGUI.addEliminarListener(e -> eliminarProducto());
        vistaProductoGUI.addGenerarReporteListener(e -> generarReporte());
    
        vistaProductoGUI.addBuscarListener(e -> {
            String texto = vistaProductoGUI.getTextoBusqueda();
            if (texto.isEmpty()) {
                listarProductos();
                return;
            }
    
            try {
                if (texto.matches("\\d+")) {
                    int id = Integer.parseInt(texto);
                    producto p = almacenProductos.buscarPorId(id);
                    if (p != null) {
                        vistaProductoGUI.mostrarListaProductos(List.of(p));
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró producto con ID: " + id);
                    }
                } else {
                    List<producto> resultados = almacenProductos.filtrarPorNombre(texto);
                    vistaProductoGUI.mostrarListaProductos(resultados);
                    if (resultados.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron productos con ese nombre.");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al filtrar: " + ex.getMessage());
            }
        });
        
        vistaProductoGUI.addFiltrarListener(e -> {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();
            
            if ("Aplicar Filtro".equals(buttonText)) {
                String criterio = vistaProductoGUI.getCriterioBusqueda();
                String valorMin = vistaProductoGUI.getValorMinBusqueda();
                String valorMax = vistaProductoGUI.getValorMaxBusqueda();
                filtrarProductos(criterio, valorMin, valorMax);
            } else if ("Mostrar Todos".equals(buttonText)) {
                vistaProductoGUI.mostrarProductosFiltrados(almacenProductos.obtenerTodosProductos());
            }
        });
    }
}