package com.panaderia.controlador;

import com.panaderia.vista.vistaProductoGUI;
import com.panaderia.vista.vistaReporte;
import com.panaderia.servicio.GestorProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.modelo.producto;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que actúa como controlador para la gestión de productos.
 * Gestiona la interacción entre las vistas, los servicios y los modelos relacionados con los productos.
 */
public class controladorProducto {

    private vistaProductoGUI vistaProductoGUI; // Vista principal de productos
    private vistaReporte vistaReporte; // Vista para reportes
    private GestorProductos gestorProductos; // Servicio para gestionar productos
    private generadorReporte generadorReporte; // Servicio para generar reportes

    /**
     * Constructor de la clase controladorProducto.
     * Inicializa las vistas y servicios necesarios para la gestión de productos.
     * @param vistaProductoGUI La vista principal de productos.
     * @param vistaReporte La vista para reportes.
     * @param gestorProductos El servicio para gestionar productos.
     * @param generadorReporte El servicio para generar reportes.
     */
    public controladorProducto(vistaProductoGUI vistaProductoGUI, vistaReporte vistaReporte,
                               GestorProductos gestorProductos, generadorReporte generadorReporte) {
        this.vistaProductoGUI = vistaProductoGUI;
        this.vistaReporte = vistaReporte;
        this.gestorProductos = gestorProductos;
        this.generadorReporte = generadorReporte;
    }

    /**
     * Método que carga todos los productos desde el servicio.
     */
    public void cargarProductos() {
        gestorProductos.cargarTodos();
    }

    /**
     * Método que permite agregar un nuevo producto.
     * Solicita los datos desde la vista, valida la información y guarda el producto.
     */
    public void agregarProducto() {
        try {
            producto nuevoProducto = vistaProductoGUI.solicitarDatosProducto();
            if (nuevoProducto != null) {
                nuevoProducto.validarCosto();
                nuevoProducto.validarPrecioVenta();
                nuevoProducto.validarStockNoNegativo();

                gestorProductos.guardarProducto(nuevoProducto);
                gestorProductos.guardarTodos();
                listarProductos();
                JOptionPane.showMessageDialog(null, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método que permite actualizar un producto existente.
     * Solicita los datos desde la vista, valida la información y actualiza el producto.
     */
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

                gestorProductos.actualizarProducto(productoActualizado);
                gestorProductos.guardarTodos();
                listarProductos();
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método que permite eliminar un producto.
     * Solicita confirmación antes de proceder con la eliminación.
     */
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
            gestorProductos.eliminarProducto(idProducto);
            gestorProductos.guardarTodos();
            listarProductos();
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Método que genera un reporte en formato CSV con los productos actuales.
     * Solicita al usuario la ubicación para guardar el archivo.
     */
    public void generarReporte() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte CSV");
        fileChooser.setSelectedFile(new java.io.File("reporte_productos.csv"));

        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                List<producto> productos = gestorProductos.obtenerTodosProductos();
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

    /**
     * Método que filtra los productos según un criterio y rango de valores.
     * @param criterio El criterio de filtrado (nombre, precio, cantidad).
     * @param valorMin El valor mínimo del rango.
     * @param valorMax El valor máximo del rango.
     */
    public void filtrarProductos(String criterio, String valorMin, String valorMax) {
        List<producto> resultado = null;

        try {
            switch (criterio.toLowerCase()) {
                case "nombre":
                    resultado = gestorProductos.filtrarPorNombre(valorMin);
                    break;
                case "precio":
                    double min = Double.parseDouble(valorMin);
                    double max = Double.parseDouble(valorMax);
                    resultado = gestorProductos.filtrarPorPrecio(min, max);
                    break;
                case "cantidad":
                    int minCant = Integer.parseInt(valorMin);
                    int maxCant = Integer.parseInt(valorMax);
                    resultado = gestorProductos.filtrarPorCantidad(minCant, maxCant);
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

    /**
     * Método que obtiene todos los productos desde el servicio.
     * @return Lista de productos.
     */
    public List<producto> obtenerProductos() {
        return gestorProductos.obtenerTodosProductos();
    }

    /**
     * Método que lista todos los productos en la vista.
     */
    public void listarProductos() {
        List<producto> productos = gestorProductos.obtenerTodosProductos();
        vistaProductoGUI.mostrarListaProductos(productos);
    }

    /**
     * Método que inicializa los listeners de la vista.
     * Configura las acciones para agregar, actualizar, eliminar, generar reportes y buscar productos.
     */
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
                    producto p = gestorProductos.buscarPorId(id);
                    if (p != null) {
                        List<producto> lista = new ArrayList<>();
                        lista.add(p);
                        vistaProductoGUI.mostrarListaProductos(lista);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró producto con ID: " + id);
                    }
                } else {
                    List<producto> resultados = gestorProductos.filtrarPorNombre(texto);
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
                vistaProductoGUI.mostrarProductosFiltrados(gestorProductos.obtenerTodosProductos());
            }
        });
    }
}