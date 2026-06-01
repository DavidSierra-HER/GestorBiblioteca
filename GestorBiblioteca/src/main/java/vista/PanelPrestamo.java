package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controlador.PrestamoControlador;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Socio;

/**
 * Está es la clase principal de Panel prestamo la ventana principal para interactuar con los prestamos
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class PanelPrestamo extends JPanel {

    private static final long serialVersionUID = 1L;

    // Campos del formulario
    private JTextField txtId, txtEstado, txtFechaPrestamo, txtDevolucionEstimada, txtFechaDevolucion;
    private JTextField txtIsbnLibro, txtDniSocio;

    // Botones
    private JButton btnNuevo, btnGuardar, btnDevolver, btnEliminar;

    // Búsqueda y filtros
    private JTextField txtBuscar;
    private JButton btnBuscar, btnActivos, btnVencidos, btnDevueltos, btnTodos;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modelo;

    // Controlador.
    private PrestamoControlador controlador = new PrestamoControlador();
    
    //paginado
    private int paginaActual = 0;
    private final int TAM_PAGINA = 10;
    private JLabel lblPagina;


    public PanelPrestamo(Runnable volverInicio) {
        setLayout(new BorderLayout(0, 0));

        
      // Panel norte con buscar y filtros
      JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
     
      // Botón volver
      JButton btnVolver = new JButton("Volver");
      panelNorte.add(btnVolver);
      panelNorte.add(new JLabel("Buscar:"));
      txtBuscar = new JTextField(15);
      btnBuscar = new JButton("Buscar");
      panelNorte.add(txtBuscar);
      panelNorte.add(btnBuscar);

      // Filtros
      btnTodos = new JButton("Todos");
      btnActivos = new JButton("Activos");
      btnVencidos = new JButton("Vencidos");
      btnDevueltos = new JButton("Devueltos");
      panelNorte.add(btnTodos);
      panelNorte.add(btnActivos);
      panelNorte.add(btnVencidos);
      panelNorte.add(btnDevueltos);

      

      add(panelNorte, BorderLayout.NORTH);


        // Tabla
        String[] columnas = {"ID", "Estado", "Fecha Préstamo", "Dev. Estimada", "Fecha Devolución", "ISBN Libro", "DNI Socio"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));


  
     // PANEL SUR COMPLETO
    

     JPanel panelSur = new JPanel(new BorderLayout());

     // Panel contenedor vertical para formulario + botones + paginado
     JPanel panelInferior = new JPanel();
     panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

     // Formulario
     JPanel panelFormulario = new JPanel(new GridLayout(5, 4, 8, 8));

     // Fila 1
     panelFormulario.add(new JLabel("ID:"));
     txtId = new JTextField();
     panelFormulario.add(txtId);
     panelFormulario.add(new JLabel("Estado:"));
     txtEstado = new JTextField();
     panelFormulario.add(txtEstado);

     // Fila 2
     panelFormulario.add(new JLabel("Fecha Préstamo (YYYY-MM-DD):"));
     txtFechaPrestamo = new JTextField();
     panelFormulario.add(txtFechaPrestamo);
     panelFormulario.add(new JLabel("Dev. Estimada (YYYY-MM-DD):"));
     txtDevolucionEstimada = new JTextField();
     panelFormulario.add(txtDevolucionEstimada);

     // Fila 3
     panelFormulario.add(new JLabel("Fecha Devolución (YYYY-MM-DD):"));
     txtFechaDevolucion = new JTextField();
     panelFormulario.add(txtFechaDevolucion);
     panelFormulario.add(new JLabel("ISBN Libro:"));
     txtIsbnLibro = new JTextField();
     panelFormulario.add(txtIsbnLibro);

     // Fila 4
     panelFormulario.add(new JLabel("DNI Socio:"));
     txtDniSocio = new JTextField();
     panelFormulario.add(txtDniSocio);
     panelFormulario.add(new JLabel(""));
     panelFormulario.add(new JLabel(""));

     // botones del formulario
     JPanel panelBotones = new JPanel(new FlowLayout());
     btnNuevo = new JButton("Nuevo");
     btnGuardar = new JButton("Guardar");
     btnDevolver = new JButton("Devolver");
     btnEliminar = new JButton("Eliminar");

     panelBotones.add(btnNuevo);
     panelBotones.add(btnGuardar);
     panelBotones.add(btnDevolver);
     panelBotones.add(btnEliminar);

     // estructura del paginado
     JButton btnAnterior = new JButton("Anterior");
     JButton btnSiguiente = new JButton("Siguiente");
     lblPagina = new JLabel("Página: 1");

     JPanel panelPaginado = new JPanel(new FlowLayout(FlowLayout.CENTER));
     panelPaginado.add(btnAnterior);
     panelPaginado.add(lblPagina);
     panelPaginado.add(btnSiguiente);

     // añadimos todos los elementos en la parte sur con el border layaout
     panelInferior.add(panelFormulario);
     panelInferior.add(panelBotones);
     panelInferior.add(panelPaginado);

     panelSur.add(panelInferior, BorderLayout.CENTER);
     add(panelSur, BorderLayout.SOUTH);

     
        // Carga inicial
        cargarPagina();


        // ActionListeners
        btnVolver.addActionListener(e -> volverInicio.run());
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnDevolver.addActionListener(e -> devolver());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        btnTodos.addActionListener(e -> cargarTabla());
        btnActivos.addActionListener(e -> cargarActivos());
        btnVencidos.addActionListener(e -> cargarVencidos());
        btnDevueltos.addActionListener(e -> cargarDevueltos());
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
        btnSiguiente.addActionListener(e -> {
            paginaActual++;
            cargarPagina();
        });

        btnAnterior.addActionListener(e -> {
            if (paginaActual > 0) {
                paginaActual--;
                cargarPagina();
            }
        });

    }

    // Carga todos los préstamos
    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Prestamo> prestamos = controlador.obtener();
        for (Prestamo p : prestamos) {
            modelo.addRow(new Object[]{
                p.getId(), p.getEstado(), p.getFechaPrestado(),
                p.getDevolucionEstimada(), p.getFechaDevolucion(),
                p.getLibroPrestado().getIsbn(),
                p.getSocioPrestamo().getDni()
            });
        }
    }

    // Carga solo activos
    private void cargarActivos() {
        modelo.setRowCount(0);
        List<Prestamo> prestamos = controlador.buscarPrestamosActivos();
        for (Prestamo p : prestamos) {
            modelo.addRow(new Object[]{
                p.getId(), p.getEstado(), p.getFechaPrestado(),
                p.getDevolucionEstimada(), p.getFechaDevolucion(),
                p.getLibroPrestado().getIsbn(),
                p.getSocioPrestamo().getDni()
            });
        }
    }

    // Carga solo vencidos
    private void cargarVencidos() {
        modelo.setRowCount(0);
        List<Prestamo> prestamos = controlador.obtenerVencidos();
        for (Prestamo p : prestamos) {
            modelo.addRow(new Object[]{
                p.getId(), p.getEstado(), p.getFechaPrestado(),
                p.getDevolucionEstimada(), p.getFechaDevolucion(),
                p.getLibroPrestado().getIsbn(),
                p.getSocioPrestamo().getDni()
            });
        }
    }

    // Carga solo devueltos
    private void cargarDevueltos() {
        modelo.setRowCount(0);
        List<Prestamo> prestamos = controlador.obtenerDevueltos();
        for (Prestamo p : prestamos) {
            modelo.addRow(new Object[]{
                p.getId(), p.getEstado(), p.getFechaPrestado(),
                p.getDevolucionEstimada(), p.getFechaDevolucion(),
                p.getLibroPrestado().getIsbn(),
                p.getSocioPrestamo().getDni()
            });
        }
    }

    // Limpia el formulario
    private void limpiarFormulario() {
        txtId.setText("");
        txtEstado.setText("");
        txtFechaPrestamo.setText("");
        txtDevolucionEstimada.setText("");
        txtFechaDevolucion.setText("");
        txtIsbnLibro.setText("");
        txtDniSocio.setText("");
    }

    // Rellena formulario al seleccionar fila
    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modelo.getValueAt(fila, 0).toString());
            txtEstado.setText(modelo.getValueAt(fila, 1).toString());
            txtFechaPrestamo.setText(modelo.getValueAt(fila, 2).toString());
            txtDevolucionEstimada.setText(modelo.getValueAt(fila, 3).toString());
            Object fechaDev = modelo.getValueAt(fila, 4);
            txtFechaDevolucion.setText(fechaDev != null ? fechaDev.toString() : "");
            txtIsbnLibro.setText(modelo.getValueAt(fila, 5).toString());
            txtDniSocio.setText(modelo.getValueAt(fila, 6).toString());
        }
    }

    // Guarda un préstamo nuevo
    private void guardar() {
        try {
            // Buscamos el libro y socio por ISBN y DNI
            Libro libro = new Libro(txtIsbnLibro.getText(), "", "", "", "", 0);
            Socio socio = new Socio(txtDniSocio.getText(), "", "", "", null);

            LocalDate fechaDev = txtFechaDevolucion.getText().isEmpty() ?
                null : LocalDate.parse(txtFechaDevolucion.getText());

            Prestamo prestamo = new Prestamo(
                txtId.getText(),
                txtEstado.getText().isEmpty() ? "ACTIVO" : txtEstado.getText(),
                LocalDate.parse(txtFechaPrestamo.getText()),
                LocalDate.parse(txtDevolucionEstimada.getText()),
                fechaDev,
                libro,
                socio
            );
            controlador.registrar(prestamo);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Préstamo guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Devuelve el préstamo seleccionado
    private void devolver() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo de la tabla.");
            return;
        }
        try {
            Libro libro = new Libro(txtIsbnLibro.getText(), "", "", "", "", 0);
            Socio socio = new Socio(txtDniSocio.getText(), "", "", "", null);
            Prestamo prestamo = new Prestamo(
                txtId.getText(),
                txtEstado.getText(),
                LocalDate.parse(txtFechaPrestamo.getText()),
                LocalDate.parse(txtDevolucionEstimada.getText()),
                null,
                libro,
                socio
            );
            controlador.devolver(prestamo);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Préstamo devuelto correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Elimina el préstamo seleccionado
    private void eliminar() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este préstamo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminar(Integer.parseInt(txtId.getText()));
                cargarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Préstamo eliminado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Busca préstamos por estado o ISBN
    private void buscar() {
        try {
            modelo.setRowCount(0);
            List<Prestamo> prestamos = controlador.obtener();
            String texto = txtBuscar.getText().toLowerCase();
            for (Prestamo p : prestamos) {
                if (p.getEstado().toLowerCase().contains(texto) ||
                    p.getLibroPrestado().getIsbn().toLowerCase().contains(texto) ||
                    p.getSocioPrestamo().getDni().toLowerCase().contains(texto)) {
                    modelo.addRow(new Object[]{
                        p.getId(), p.getEstado(), p.getFechaPrestado(),
                        p.getDevolucionEstimada(), p.getFechaDevolucion(),
                        p.getLibroPrestado().getIsbn(),
                        p.getSocioPrestamo().getDni()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //metodo de carga de pagina
    private void cargarPagina() {
        modelo.setRowCount(0);
        List<Prestamo> prestamos = controlador.obtenerPagina(paginaActual, TAM_PAGINA);

        for (Prestamo p : prestamos) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getEstado(),
                p.getFechaPrestado(),
                p.getDevolucionEstimada(),
                p.getFechaDevolucion(),
                p.getLibroPrestado().getIsbn(),
                p.getSocioPrestamo().getDni()
            });
        }

        lblPagina.setText("Página: " + (paginaActual + 1));
    }

}
