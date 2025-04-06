package com.panaderia.app;

import com.panaderia.controlador.controladorAutenticacion;
import com.panaderia.controlador.controladorProducto;
import com.panaderia.modelo.almacenProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.vista.vistaLogin;
import com.panaderia.vista.vistaProductoGUI;
import com.panaderia.vista.vistaReporte;

public class Main {
    public static void main(String[] args) {
        vistaLogin vistaLogin = new vistaLogin();
        new controladorAutenticacion(vistaLogin) {
            @Override
            public void manejarSolicitudLogin() {
                super.manejarSolicitudLogin();
                if (super.autenticarAdministrador(vistaLogin.obtenerNombreUsuario(), 
                        vistaLogin.obtenerContraseña()) != null) {
                    iniciarAplicacion();
                }
            }
        };
        
        vistaLogin.setVisible(true);
    }
    
    private static void iniciarAplicacion() {
        vistaProductoGUI vista = new vistaProductoGUI();
        vistaReporte reporte = new vistaReporte();
        almacenProductos almacen = new almacenProductos();
        generadorReporte generador = new generadorReporte();

        controladorProducto controlador = new controladorProducto(vista, reporte, almacen, generador);
        controlador.cargarProductos();
        controlador.listarProductos();
        controlador.inicializar();

        vista.setVisible(true);
    }
}