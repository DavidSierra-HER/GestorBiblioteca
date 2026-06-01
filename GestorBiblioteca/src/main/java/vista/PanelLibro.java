package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

import controlador.LibroControlador;
import modelo.Libro;
import servicio.LibroServicio;

/**
 * Está es la clase panel de libro, la ventana principal de los libros
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class PanelLibro extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//areas de texto necesarias por cada atributo de libro.
	private JTextField txtIsbn,txtTitulo, txtAutor,txtGenero,txtAnno,txtDisponible;
	//Botones necesarios en consecuencia de las accions que necesitamos hacer
	private JButton btnNuevo,btnGuardar,btnModificar,btnEliminar;
	
	//Botones del area superior para agilizar la busqueda dentro de la tabla
	private JTextField txtBuscar;
	private JButton btnBuscar;
	
	//Controlador de Libro para llamar a los metodos
	private LibroControlador controlador = new LibroControlador();;
	
	//Para editar las tablas 
	private JTable tabla;
	private DefaultTableModel modelo;
	
	//Paginado
	private JLabel lblPagina;
	private int paginaActual = 0;
	private final int TAM_PAGINA = 10;


	/**
	 * Create the panel. Generación del panel de forma automatica
	 */
	public PanelLibro(Runnable volverInicio) {
		//Layout que nos deja editar un tabedpane a nuestro gusto.
		setLayout(new BorderLayout(0,0));
		
		
	
		
		//Estructura para colocar el buscador en la parte de Arriba izquierda, en la parte norte del Jlabel
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBusqueda.add(new JLabel("Buscar:"));
		txtBuscar = new JTextField(20);
		btnBuscar = new JButton("Buscar");
		JButton btnVolver = new JButton("Volver");
	    btnVolver.addActionListener(e -> volverInicio.run());
	    panelBusqueda.add(btnVolver);
		
		panelBusqueda.add(txtBuscar);
		panelBusqueda.add(btnBuscar);
		
		add(panelBusqueda, BorderLayout.NORTH);
		
		
		//apartado de la tabla, creamos el modelo, con las columnas e inicializada a 0
		//creamos la tabla pasandole el modelo y añadimos con un JScroll que ocupe el centro.
		
		String[] columnas = {"ISBN", "Título", "Autor", "Género", "Año","Disponibles"};
		modelo = new DefaultTableModel(columnas,0);
		tabla = new JTable(modelo);
		add(new JScrollPane(tabla),BorderLayout.CENTER);
		tabla.setFillsViewportHeight(true);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		add(new JScrollPane(tabla), BorderLayout.CENTER);
		tabla.setRowHeight(28);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		
		//metodo de carga de tabla
		cargarTabla();
		
		// Panel sur principal
		JPanel panelSur = new JPanel(new BorderLayout());

		// Panel contenedor vertical para formulario + botones + paginado
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

		// ===== FORMULARIO =====
		JPanel panelFormulario = new JPanel(new GridLayout(3,4,5,5));
		panelFormulario.add(new JLabel("ISBN:"));
		txtIsbn = new JTextField();
		panelFormulario.add(txtIsbn);
		panelFormulario.add(new JLabel("Título:"));
		txtTitulo = new JTextField();
		panelFormulario.add(txtTitulo);
		panelFormulario.add(new JLabel("Autor:"));
		txtAutor = new JTextField();
		panelFormulario.add(txtAutor);
		panelFormulario.add(new JLabel("Género:"));
		txtGenero = new JTextField();
		panelFormulario.add(txtGenero);
		panelFormulario.add(new JLabel("Año (YYYY-MM-DD):"));
		txtAnno = new JTextField();
		panelFormulario.add(txtAnno);
		panelFormulario.add(new JLabel("Disponibles:"));
		txtDisponible = new JTextField();
		panelFormulario.add(txtDisponible);

		// ===== BOTONES =====
		JPanel panelBotones = new JPanel(new FlowLayout());
		btnNuevo = new JButton("Nuevo");
		btnGuardar = new JButton("Guardar");
		btnModificar = new JButton("Modificar");
		btnEliminar = new JButton("Eliminar");

		panelBotones.add(btnNuevo);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);

		// ===== PAGINADO =====
		JButton btnAnterior = new JButton("Anterior");
		JButton btnSiguiente = new JButton("Siguiente");
		lblPagina = new JLabel("Página: 1");

		JPanel panelPaginado = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelPaginado.add(btnAnterior);
		panelPaginado.add(lblPagina);
		panelPaginado.add(btnSiguiente);

		// ===== AÑADIR TODO AL PANEL INFERIOR =====
		panelInferior.add(panelFormulario);
		panelInferior.add(panelBotones);
		panelInferior.add(panelPaginado);

		// Añadir el panel inferior al SUR del panel principal
		panelSur.add(panelInferior, BorderLayout.CENTER);

		// Finalmente añadir panelSur al panel principal
		add(panelSur, BorderLayout.SOUTH);




		
		
		//ActionListener de los botones y evento de la tabla para poder manipular las secciones de la tabla
		btnNuevo.addActionListener(e -> limpiarFormulario());
		btnGuardar.addActionListener(e ->guardar());
		btnModificar.addActionListener(e ->modificar());
		btnEliminar.addActionListener(e -> eliminar());
		btnBuscar.addActionListener(e -> buscar());
		tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
		btnSiguiente.addActionListener(e -> {paginaActual++;cargarPagina();});

		btnAnterior.addActionListener(e -> {
		    if (paginaActual > 0) {
		        paginaActual--;
		        cargarPagina();
		    }
		});

		
		
	}
	
	

	
	//Este metodo nos permite seleccionar una fila de la tabla y pone los txt field como en la fila.
	private void seleccionarFila() {
		
		int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIsbn.setText(modelo.getValueAt(fila, 0).toString());
            txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
            txtAutor.setText(modelo.getValueAt(fila, 2).toString());
            txtGenero.setText(modelo.getValueAt(fila, 3).toString());
            txtAnno.setText(modelo.getValueAt(fila, 4).toString());
            txtDisponible.setText(modelo.getValueAt(fila, 5).toString());
        }
		
	}

	
	//Se busca por titulo del libro
	private void buscar() {
		 try {
	            modelo.setRowCount(0);
	            List<Libro> libros = controlador.obtenerLibros();
	            String texto = txtBuscar.getText().toLowerCase();
	            for (Libro l : libros) {
	                if (l.getTitulo().toLowerCase().contains(texto) ||
	                    l.getAutor().toLowerCase().contains(texto) ||
	                    l.getIsbn().toLowerCase().contains(texto)) {
	                    modelo.addRow(new Object[]{
	                        l.getIsbn(), l.getTitulo(), l.getAutor(),
	                        l.getGenero(), l.getAnno(), l.getDisponibles()
	                    });
	                }
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	//Se elimina de la BBDD todos los datos del libro
	private void eliminar() {
		int confirmacion = JOptionPane.showConfirmDialog(this,
	            "¿Está seguro de eliminar este libro?", "Confirmar", JOptionPane.YES_NO_OPTION);
	        if (confirmacion == JOptionPane.YES_OPTION) {
	            try {
	                controlador.eliminar(txtIsbn.getText());
	                cargarTabla();
	                limpiarFormulario();
	                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }

	
	//Modificamos el libro seleccionadoo de la tabla de registro
	private void modificar() {
		  try {
	            Libro libro = new Libro(
	                txtIsbn.getText(),
	                txtTitulo.getText(),
	                txtAutor.getText(),
	                txtGenero.getText(),
	                txtAnno.getText(),
	                Integer.parseInt(txtDisponible.getText())
	            );
	            controlador.actualizar(libro);
	            cargarTabla();
	            limpiarFormulario();
	            JOptionPane.showMessageDialog(this, "Libro modificado correctamente.");
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	

	//Se guarda el contenido de los textfield directamente en la base de datos de forma permanente
	private void guardar() {
		try {
            Libro libro = new Libro(
                txtIsbn.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                txtGenero.getText(),
                txtAnno.getText(),
                Integer.parseInt(txtDisponible.getText())
            );
            controlador.registrarLibro(libro);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Libro guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	//Se limpia el contenido de los recuadros dejandolos vacios
	private void limpiarFormulario() {
		 	txtIsbn.setText("");
	        txtTitulo.setText("");
	        txtAutor.setText("");
	        txtGenero.setText("");
	        txtAnno.setText("");
	        txtDisponible.setText("");
	}

	
	//inicializamos las filas a 0 y despúes se carga de forma automatica los datos
	private void cargarTabla() {
		
		modelo.setRowCount(0);
		
		List<Libro> libros = controlador.obtenerLibros();
		for(Libro l : libros ) {
			modelo.addRow(new Object[] {
					l.getIsbn(), l.getTitulo(), l.getAutor(),
	                l.getGenero(), l.getAnno(), l.getDisponibles()
			});
		}
		
		
	}
	
	private void cargarPagina() {
	    modelo.setRowCount(0);
	    List<Libro> libros = LibroServicio.getInstance().obtenerPagina(paginaActual, TAM_PAGINA);

	    for (Libro l : libros) {
	        modelo.addRow(new Object[]{
	            l.getIsbn(),
	            l.getTitulo(),
	            l.getAutor(),
	            l.getGenero(),
	            l.getAnno(),
	            l.getDisponibles()
	        });
	    }

	    lblPagina.setText("Página: " + (paginaActual + 1));
	}

	
	
	
}
