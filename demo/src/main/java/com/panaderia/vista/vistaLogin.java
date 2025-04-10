package com.panaderia.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que representa la interfaz gráfica de inicio de sesión para el sistema de panadería.
 * Permite al usuario ingresar sus credenciales y manejar eventos relacionados con el inicio de sesión.
 */
public class vistaLogin extends JFrame {
    private JTextField txtUsuario; // Campo de texto para el nombre de usuario
    private JPasswordField txtPassword; // Campo de texto para la contraseña
    private JButton btnLogin; // Botón para iniciar sesión

    /**
     * Constructor de la clase vistaLogin.
     * Configura la ventana principal y sus componentes.
     */
    public vistaLogin() {
        setTitle("Login - Sistema de Panadería");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        initComponents(); // Inicializar los componentes de la interfaz
    }

    /**
     * Método privado que inicializa los componentes de la interfaz gráfica.
     * Configura el diseño, los campos de texto y el botón de inicio de sesión.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Panel con diseño de cuadrícula
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado interno

        // Etiqueta y campo de texto para el usuario
        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        // Etiqueta y campo de texto para la contraseña
        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        // Espacio vacío y botón de inicio de sesión
        panel.add(new JLabel(""));
        btnLogin = new JButton("Iniciar sesión");
        panel.add(btnLogin);

        // Agregar el panel a la ventana principal
        add(panel);
    }

    /**
     * Obtiene el nombre de usuario ingresado en el campo de texto.
     * @return El nombre de usuario como una cadena de texto.
     */
    public String obtenerNombreUsuario() {
        return txtUsuario.getText().trim();
    }

    /**
     * Obtiene la contraseña ingresada en el campo de texto.
     * @return La contraseña como una cadena de texto.
     */
    public String obtenerContraseña() {
        return new String(txtPassword.getPassword());
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     * @param mensaje El mensaje de error a mostrar.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de autenticación", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito en un cuadro de diálogo.
     * @param mensaje El mensaje de éxito a mostrar.
     */
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Cierra la ventana de inicio de sesión.
     */
    public void cerrar() {
        this.dispose();
    }

    /**
     * Agrega un listener para manejar eventos del botón de inicio de sesión.
     * @param listener El listener que manejará los eventos del botón.
     */
    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
}