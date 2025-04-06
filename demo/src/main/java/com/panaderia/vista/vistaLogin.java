package com.panaderia.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class vistaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    public vistaLogin() {
        setTitle("Login - Sistema de Panadería");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel(""));
        btnLogin = new JButton("Iniciar sesión");
        panel.add(btnLogin);

        add(panel);
    }

    public String obtenerNombreUsuario() {
        return txtUsuario.getText().trim();
    }

    public String obtenerContraseña() {
        return new String(txtPassword.getPassword());
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de autenticación", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void cerrar() {
        this.dispose();
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
}