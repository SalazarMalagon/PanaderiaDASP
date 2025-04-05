package com.panaderia.vista;

import com.panaderia.modelo.producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
public class vistaProductoGUI extends JFrame {
    private JTextField txtNombre, txtStock, txtCosto, txtPrecio, txtId, txtBuscar;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnBuscar;
    private JTable tablaProductos;
    private DefaultTableModel tableModel;

    public vistaProductoGUI() {
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null); // Centra la ventana
    }

    private void initComponents() {
        // Panel del formulario para ingresar datos del producto
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // Solo lectura
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

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnActualizar);
        panelFormulario.add(btnEliminar);

        // Panel de la tabla para mostrar productos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Stock", "Costo", "Precio"}, 0);
        tablaProductos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Layout principal
        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        agregarPanelBusqueda();

        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                llenarFormularioDesdeTabla();
            }
        });
    }

    // Método para solicitar los datos del producto (usando los campos de texto)
    public producto solicitarDatosProducto() {
        String nombre = txtNombre.getText();
        int stock = Integer.parseInt(txtStock.getText());
        double costo = Double.parseDouble(txtCosto.getText());
        double precio = Double.parseDouble(txtPrecio.getText());
        return new producto(0, nombre, stock, costo, precio); // ID se asignará en el controlador
    }

    // Método para mostrar la lista de productos en la tabla
    public void mostrarListaProductos(List<producto> productos) {
        tableModel.setRowCount(0); // Limpiar tabla
        for (producto p : productos) {
            tableModel.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getStock(),
                p.getCosto(),
                p.getPrecio()
            });
        }
    }

    // Permite agregar un listener al botón "Agregar Producto"
    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public int obtenerIdProductoSeleccionado() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            return (int) tableModel.getValueAt(filaSeleccionada, 0);
        } else {
            return -1; // Ninguna fila seleccionada
        }
    }

    public void addActualizarListener(ActionListener listener) {
        btnActualizar.addActionListener(listener);
    }
    
    public void addEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    public void llenarFormularioDesdeTabla() {
        int fila = tablaProductos.getSelectedRow();
        if (fila != -1) {
            txtId.setText(String.valueOf(tableModel.getValueAt(fila, 0)));
            txtNombre.setText(String.valueOf(tableModel.getValueAt(fila, 1)));
            txtStock.setText(String.valueOf(tableModel.getValueAt(fila, 2)));
            txtCosto.setText(String.valueOf(tableModel.getValueAt(fila, 3)));
            txtPrecio.setText(String.valueOf(tableModel.getValueAt(fila, 4)));
        }
    }

    public producto obtenerProductoFormulario() {
        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        int stock = Integer.parseInt(txtStock.getText());
        double costo = Double.parseDouble(txtCosto.getText());
        double precio = Double.parseDouble(txtPrecio.getText());
        return new producto(id, nombre, stock, costo, precio);
    }

    private void agregarPanelBusqueda() {
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Producto"));
    
        panelBusqueda.add(new JLabel("Buscar (ID o Nombre):"));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
    
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
    
        add(panelBusqueda, BorderLayout.SOUTH); // Lo ponemos al sur para que no choque con el formulario ni la tabla
    }
    
    public String getTextoBusqueda() {
        return txtBuscar.getText();
    }

    public void addBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }
    
}