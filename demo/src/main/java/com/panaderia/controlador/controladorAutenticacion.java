package com.panaderia.controlador;

import com.panaderia.vista.vistaLogin;
import com.panaderia.modelo.administrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como controlador para la autenticación de administradores.
 * Gestiona la lógica de inicio de sesión y la interacción entre la vista de login y el modelo de administrador.
 */
public class controladorAutenticacion {
    private vistaLogin vistaLogin; // Vista de inicio de sesión
    private List<administrador> administradores; // Lista de administradores registrados
    
    /**
     * Constructor de la clase controladorAutenticacion.
     * Inicializa la vista de login y la lista de administradores.
     * @param vistaLogin La vista de inicio de sesión que será controlada.
     */
    public controladorAutenticacion(vistaLogin vistaLogin) {
        this.vistaLogin = vistaLogin;
        this.administradores = new ArrayList<>();
        
        // Agregar un administrador predeterminado a la lista
        administradores.add(new administrador(1, "admin", "admin@panaderia.com", "admin123"));
        
        // Configurar los listeners de la vista
        inicializar();
    }
    
    /**
     * Método privado que inicializa los listeners de la vista.
     * Configura el comportamiento del botón de inicio de sesión.
     */
    private void inicializar() {
        vistaLogin.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarSolicitudLogin();
            }
        });
    }
    
    /**
     * Método que maneja la solicitud de inicio de sesión.
     * Verifica las credenciales ingresadas por el usuario y muestra mensajes de éxito o error.
     */
    public void manejarSolicitudLogin() {
        String usuario = vistaLogin.obtenerNombreUsuario(); // Obtener el nombre de usuario ingresado
        String contraseña = vistaLogin.obtenerContraseña(); // Obtener la contraseña ingresada
        
        // Intentar autenticar al administrador
        administrador admin = autenticarAdministrador(usuario, contraseña);
        
        if (admin != null) {
            // Si la autenticación es exitosa, mostrar mensaje de bienvenida y cerrar la vista
            vistaLogin.mostrarExito("Bienvenido " + admin.getnombre());
            vistaLogin.cerrar();
        } else {
            // Si la autenticación falla, mostrar mensaje de error
            vistaLogin.mostrarError("Usuario o contraseña incorrectos");
        }
    }
    
    /**
     * Método que autentica a un administrador basado en su nombre de usuario y contraseña.
     * @param nombreUsuario El nombre de usuario ingresado.
     * @param contraseña La contraseña ingresada.
     * @return El objeto administrador si las credenciales son correctas, de lo contrario null.
     */
    public administrador autenticarAdministrador(String nombreUsuario, String contraseña) {
        for (administrador admin : administradores) {
            // Verificar si el nombre de usuario y la contraseña coinciden
            if (admin.getnombre().equals(nombreUsuario) && admin.verificarContraseña(contraseña)) {
                return admin;
            }
        }
        return null; // Retornar null si no se encuentra un administrador válido
    }
}