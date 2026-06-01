package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controlador.SocioControlador;
import modelo.Socio;

public class PanelSocio extends JPanel {

    private static final long serialVersionUID = 1L;

    // Campos del formulario
    private JTextField txtDni, txtNombre, txtDireccion, txtTlfn, txtAlta;

    // Botones de acción
    private JButton btnNuevo, btnGuardar, btnModificar, btnEliminar;

    // Búsqueda
    private JTextField txtBuscar;
    private JButton btnBuscar;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modelo;

    // Controlador
    private SocioControlador controlador = new SocioControlador();

    public PanelSocio(Runnable volverInicio) {
        setLayout(new BorderLayout(0, 0));

        // Panel búsqueda y botón volver arriba
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("← Volver");
        panelBusqueda.add(btnVolver);
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"DNI", "Nombre", "Dirección", "Teléfono", "Alta"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel sur con formulario y botones
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 5, 5));
        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Teléfono:"));
        txtTlfn = new JTextField();
        panelFormulario.add(txtTlfn);
        panelFormulario.add(new JLabel("Fecha Alta (YYYY-MM-DD):"));
        txtAlta = new JTextField();
        panelFormulario.add(txtAlta);
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        panelSur.add(panelFormulario, BorderLayout.CENTER);
        panelSur.add(panelBotones, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

        // Carga inicial
        cargarTabla();

        // ActionListeners
        btnVolver.addActionListener(e -> volverInicio.run());
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
    }

    // Carga todos los socios en la tabla
    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Socio> socios = controlador.obtener();
        for (Socio s : socios) {
            modelo.addRow(new Object[]{
                s.getDni(), s.getNombre(), s.getDireccion(),
                s.getTlfn(), s.getAlta()
            });
        }
    }

    // Limpia los campos del formulario
    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTlfn.setText("");
        txtAlta.setText("");
    }

    // Rellena el formulario al seleccionar una fila
    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtDni.setText(modelo.getValueAt(fila, 0).toString());
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtDireccion.setText(modelo.getValueAt(fila, 2).toString());
            txtTlfn.setText(modelo.getValueAt(fila, 3).toString());
            txtAlta.setText(modelo.getValueAt(fila, 4).toString());
        }
    }

    // Guarda un socio nuevo
    private void guardar() {
        try {
            Socio socio = new Socio(
                txtDni.getText(),
                txtNombre.getText(),
                txtDireccion.getText(),
                txtTlfn.getText(),
                java.time.LocalDate.parse(txtAlta.getText())
            );
            controlador.registrar(socio);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Socio guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Modifica el socio seleccionado.
    private void modificar() {
        try {
            Socio socio = new Socio(
                txtDni.getText(),
                txtNombre.getText(),
                txtDireccion.getText(),
                txtTlfn.getText(),
                java.time.LocalDate.parse(txtAlta.getText())
            );
            controlador.actualizar(socio);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Socio modificado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Elimina el socio seleccionado
    private void eliminar() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este socio?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminar(txtDni.getText());
                cargarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Socio eliminado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Busca socios por DNI o nombre
    private void buscar() {
        try {
            modelo.setRowCount(0);
            List<Socio> socios = controlador.obtener();
            String texto = txtBuscar.getText().toLowerCase();
            for (Socio s : socios) {
                if (s.getDni().toLowerCase().contains(texto) ||
                    s.getNombre().toLowerCase().contains(texto)) {
                    modelo.addRow(new Object[]{
                        s.getDni(), s.getNombre(), s.getDireccion(),
                        s.getTlfn(), s.getAlta()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}