package com.panaderia.app;

import com.panaderia.controlador.controladorProducto;
import com.panaderia.modelo.almacenProductos;
import com.panaderia.reportes.generadorReporte;
import com.panaderia.vista.vistaProductoGUI;
import com.panaderia.vista.vistaReporte;


public class Main {
    public static void main(String[] args) {
        vistaProductoGUI vista = new vistaProductoGUI();
    vistaReporte reporte = new vistaReporte();
    almacenProductos almacen = new almacenProductos();
    generadorReporte generador = new generadorReporte();

    controladorProducto controlador = new controladorProducto(vista, reporte, almacen, generador);
    controlador.cargarProductos();
    controlador.listarProductos();
    controlador.inicializar(); // 👈 Importante

    vista.setVisible(true);
    }
}
