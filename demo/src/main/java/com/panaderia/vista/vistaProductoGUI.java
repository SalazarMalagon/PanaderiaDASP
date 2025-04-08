package com.panaderia.vista;

import com.panaderia.modelo.galleta;
import com.panaderia.modelo.pan;
import com.panaderia.modelo.producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class vistaProductoGUI extends JFrame {
    private JTextField txtNombre, txtStock, txtCosto, txtPrecio, txtId, txtBuscar;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnBuscar, btnGenerarReporte;
    private JTable tablaProductos, tablaFiltros;
    private DefaultTableModel tableModel, modeloFiltros;
    private JComboBox<String> comboTipoProducto;
    private JCheckBox checkCaracteristica;
    private JComboBox<String> comboCriterio;
    private JTextField txtValorMin, txtValorMax;
    private JButton btnAplicarFiltro, btnLimpiarFiltro;

    public vistaProductoGUI() {
        setTitle("Gestión de Productos - Panadería");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel panelAdmin = crearPanelAdministracion();
        tabbedPane.addTab("Administrar Productos", panelAdmin);
        
        JPanel panelFiltros = crearPanelFiltros();
        tabbedPane.addTab("Filtrar Productos", panelFiltros);
        
        JPanel panelReportes = crearPanelReportes();
        tabbedPane.addTab("Generar Reportes", panelReportes);
        
        add(tabbedPane);
    }

    private JPanel crearPanelAdministracion() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        
        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);
        
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        
        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);
        
        panelFormulario.add(new JLabel("Costo:"));
        txtCosto = new JTextField();
        panelFormulario.add(txtCosto);
        
        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);
        
        panelFormulario.add(new JLabel("Tipo de Producto:"));
        comboTipoProducto = new JComboBox<>(new String[] {"Genérico", "Pan", "Galleta"});
        panelFormulario.add(comboTipoProducto);
        
        checkCaracteristica = new JCheckBox("Tiene Queso/Chispas");
        comboTipoProducto.addActionListener(e -> {
            actualizarCheckboxCaracteristica();
        });
        panelFormulario.add(checkCaracteristica);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar"));
        panelBusqueda.add(new JLabel("Buscar (ID o Nombre):"));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Stock", "Costo", "Precio", "Tipo", "Características"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        tablaProductos.setFillsViewportHeight(true);
        
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                llenarFormularioDesdeTabla();
            }
        });
        
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBusqueda, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel panelCriterios = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCriterios.setBorder(BorderFactory.createTitledBorder("Filtrar por:"));
        
        panelCriterios.add(new JLabel("Criterio:"));
        comboCriterio = new JComboBox<>(new String[]{"Precio", "Nombre", "Cantidad"});
        panelCriterios.add(comboCriterio);
        
        panelCriterios.add(new JLabel("Valor (min):"));
        txtValorMin = new JTextField("0");
        panelCriterios.add(txtValorMin);
        
        panelCriterios.add(new JLabel("Valor (max):"));
        txtValorMax = new JTextField("99999");
        panelCriterios.add(txtValorMax);
        
        JPanel panelBotones = new JPanel();
        btnAplicarFiltro = new JButton("Aplicar Filtro");
        btnLimpiarFiltro = new JButton("Mostrar Todos");
        panelBotones.add(btnAplicarFiltro);
        panelBotones.add(btnLimpiarFiltro);
        
        modeloFiltros = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Stock", "Costo", "Precio", "Tipo", "Características"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaFiltros = new JTable(modeloFiltros);
        JScrollPane scrollFiltros = new JScrollPane(tablaFiltros);
        tablaFiltros.setFillsViewportHeight(true);
        
        comboCriterio.addActionListener(e -> {
            String criterio = (String) comboCriterio.getSelectedItem();
            if ("Nombre".equals(criterio)) {
                txtValorMin.setText("");
                txtValorMax.setText("");
                txtValorMin.setEnabled(true);
                txtValorMax.setEnabled(false);
            } else {
                txtValorMin.setText("0");
                txtValorMax.setText("99999");
                txtValorMin.setEnabled(true);
                txtValorMax.setEnabled(true);
            }
        });
        
        panel.add(panelCriterios, BorderLayout.NORTH);
        panel.add(scrollFiltros, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("Generar Reporte CSV de Productos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel lblDescripcion = new JLabel("<html>Esta función genera un archivo CSV con todos los productos<br>"
                + "actualmente en el sistema, incluyendo sus detalles específicos como ID, nombre,<br>"
                + "stock, costo, precio, tipo de producto y características especiales.</html>");
        
        JTextField txtRutaReporte = new JTextField("reporte_productos.csv", 30);
        JPanel panelRuta = new JPanel();
        panelRuta.add(new JLabel("Ruta del archivo:"));
        panelRuta.add(txtRutaReporte);
        
        btnGenerarReporte = new JButton("Generar Reporte CSV");
        JLabel lblEstado = new JLabel(" ", JLabel.CENTER);
        
        panelCentral.add(lblTitulo, gbc);
        panelCentral.add(Box.createVerticalStrut(20), gbc);
        panelCentral.add(lblDescripcion, gbc);
        panelCentral.add(Box.createVerticalStrut(20), gbc);
        panelCentral.add(panelRuta, gbc);
        panelCentral.add(Box.createVerticalStrut(20), gbc);
        panelCentral.add(btnGenerarReporte, gbc);
        panelCentral.add(lblEstado, gbc);
        
        panel.add(panelCentral, BorderLayout.CENTER);
        
        return panel;
    }

    private void actualizarCheckboxCaracteristica() {
        String selected = (String) comboTipoProducto.getSelectedItem();
        if ("Pan".equals(selected)) {
            checkCaracteristica.setText("Tiene Queso");
            checkCaracteristica.setEnabled(true);
        } else if ("Galleta".equals(selected)) {
            checkCaracteristica.setText("Tiene Chispas");
            checkCaracteristica.setEnabled(true);
        } else {
            checkCaracteristica.setText("Tiene Queso/Chispas");
            checkCaracteristica.setEnabled(false);
            checkCaracteristica.setSelected(false);
        }
    }

    public producto solicitarDatosProducto() {
        try {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El nombre del producto no puede estar vacío.", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            int stock = Integer.parseInt(txtStock.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            String tipo = (String) comboTipoProducto.getSelectedItem();
            boolean caracteristica = checkCaracteristica.isSelected();
            
            if ("Pan".equalsIgnoreCase(tipo)) {
                return new pan(0, nombre, stock, costo, precio, caracteristica);
            } else if ("Galleta".equalsIgnoreCase(tipo)) {
                return new galleta(0, nombre, stock, costo, precio, caracteristica);
            } else {
                return new producto(0, nombre, stock, costo, precio);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese valores numéricos válidos para stock, costo y precio.", 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void mostrarListaProductos(List<producto> productos) {
        tableModel.setRowCount(0); 
        for (producto p : productos) {
            mostrarProductoEnTabla(p, tableModel);
        }
    }
    
    public void mostrarProductosFiltrados(List<producto> productos) {
        modeloFiltros.setRowCount(0); 
        for (producto p : productos) {
            mostrarProductoEnTabla(p, modeloFiltros);
        }
    }
    
    private void mostrarProductoEnTabla(producto p, DefaultTableModel model) {
        String tipo = "Genérico";
        String caracteristica = "N/A";
        
        if (p instanceof pan) {
            tipo = "Pan";
            caracteristica = "Queso: " + (((pan) p).getTieneQueso() ? "Sí" : "No");
        } else if (p instanceof galleta) {
            tipo = "Galleta";
            caracteristica = "Chispas: " + (((galleta) p).getTieneChispas() ? "Sí" : "No");
        }
        
        model.addRow(new Object[]{
            p.getIdProducto(),
            p.getNombre(),
            p.getStock(),
            p.getCosto(),
            p.getPrecio(),
            tipo,
            caracteristica
        });
    }

    public void llenarFormularioDesdeTabla() {
        int fila = tablaProductos.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tableModel.getValueAt(fila, 0).toString());
            txtNombre.setText(tableModel.getValueAt(fila, 1).toString());
            txtStock.setText(tableModel.getValueAt(fila, 2).toString());
            txtCosto.setText(tableModel.getValueAt(fila, 3).toString());
            txtPrecio.setText(tableModel.getValueAt(fila, 4).toString());
            
            String tipo = tableModel.getValueAt(fila, 5).toString();
            comboTipoProducto.setSelectedItem(tipo);
            actualizarCheckboxCaracteristica();
            
            String caracteristica = tableModel.getValueAt(fila, 6).toString();
            checkCaracteristica.setSelected(caracteristica.contains("Sí"));
        }
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public int obtenerIdProductoSeleccionado() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            return (int) tableModel.getValueAt(filaSeleccionada, 0);
        } else {
            return -1;
        }
    }

    public String getCriterioBusqueda() {
        return (String) comboCriterio.getSelectedItem();
    }

    public String getValorMinBusqueda() {
        return txtValorMin.getText().trim();
    }

    public String getValorMaxBusqueda() {
        return txtValorMax.getText().trim();
    }

    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void addActualizarListener(ActionListener listener) {
        btnActualizar.addActionListener(listener);
    }

    public void addEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    public void addBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }
    
    public void addGenerarReporteListener(ActionListener listener) {
        btnGenerarReporte.addActionListener(listener);
    }

    public void addFiltrarListener(ActionListener listener) {
        btnAplicarFiltro.addActionListener(listener);
        btnLimpiarFiltro.addActionListener(listener);
    }
}