package com.panaderia.controlador;

import com.panaderia.vista.vistaLogin;
import com.panaderia.modelo.administrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class controladorAutenticacion {
    private vistaLogin vistaLogin;
    private List<administrador> administradores;
    
    public controladorAutenticacion(vistaLogin vistaLogin) {
        this.vistaLogin = vistaLogin;
        this.administradores = new ArrayList<>();
        
        administradores.add(new administrador(1, "admin", "admin@panaderia.com", "admin123"));
        
        inicializar();
    }
    
    private void inicializar() {
        vistaLogin.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarSolicitudLogin();
            }
        });
    }
    
    public void manejarSolicitudLogin() {
        String usuario = vistaLogin.obtenerNombreUsuario();
        String contraseña = vistaLogin.obtenerContraseña();
        
        administrador admin = autenticarAdministrador(usuario, contraseña);
        
        if (admin != null) {
            vistaLogin.mostrarExito("Bienvenido " + admin.getnombre());
            vistaLogin.cerrar();
        } else {
            vistaLogin.mostrarError("Usuario o contraseña incorrectos");
        }
    }
    
    public administrador autenticarAdministrador(String nombreUsuario, String contraseña) {
        for (administrador admin : administradores) {
            if (admin.getnombre().equals(nombreUsuario) && admin.verificarContraseña(contraseña)) {
                return admin;
            }
        }
        return null;
    }
}