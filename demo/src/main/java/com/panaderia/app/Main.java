package com.panaderia.app;

// Importaciones necesarias para el funcionamiento de la aplicación
import com.panaderia.controlador.controladorAutenticacion;
import com.panaderia.controlador.controladorProducto;
import com.panaderia.servicio.GestorProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.vista.vistaLogin;
import com.panaderia.vista.vistaProductoGUI;
import com.panaderia.vista.vistaReporte;

/**
 * Clase principal de la aplicación Panadería DASP.
 * Se encarga de inicializar la interfaz de usuario y los controladores necesarios.
 */
public class Main {
    /**
     * Método principal que inicia la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        // Crear la vista de inicio de sesión
        vistaLogin vistaLogin = new vistaLogin();

        // Crear el controlador de autenticación con una implementación personalizada
        new controladorAutenticacion(vistaLogin) {
            @Override
            public void manejarSolicitudLogin() {
                super.manejarSolicitudLogin();
                // Verificar si el usuario es un administrador
                if (super.autenticarAdministrador(vistaLogin.obtenerNombreUsuario(), 
                        vistaLogin.obtenerContraseña()) != null) {
                    // Si la autenticación es exitosa, iniciar la aplicación
                    iniciarAplicacion();
                }
            }
        };

        // Mostrar la ventana de inicio de sesión
        vistaLogin.setVisible(true);
    }
    
    /**
     * Método que inicializa la aplicación principal después de la autenticación.
     * Configura las vistas, controladores y servicios necesarios.
     */
    private static void iniciarAplicacion() {
        // Crear las vistas principales
        vistaProductoGUI vista = new vistaProductoGUI();
        vistaReporte reporte = new vistaReporte();

        // Crear el gestor de productos y el generador de reportes
        GestorProductos gestor = new GestorProductos();
        generadorReporte generador = new generadorReporte();

        // Crear el controlador de productos con las dependencias necesarias
        controladorProducto controlador = new controladorProducto(vista, reporte, gestor, generador);

        // Cargar y listar los productos en la interfaz
        controlador.cargarProductos();
        controlador.listarProductos();

        // Inicializar la lógica del controlador
        controlador.inicializar();

        // Mostrar la ventana principal de productos
        vista.setVisible(true);
    }
}