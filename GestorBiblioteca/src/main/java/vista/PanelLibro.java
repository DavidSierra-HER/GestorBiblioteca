package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controlador.LibroControlador;

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
	private LibroControlador controlador;
	
	//Para editar las tablas 
	private JTable tabla;
	private DefaultTableModel modelo;

	/**
	 * Create the panel. Generación del panel de forma automatica
	 */
	public PanelLibro() {
		//Layout que nos deja editar un tabedpane a nuestro gusto.
		setLayout(new BorderLayout(0,0));
		
		//Estructura para colocar el buscador en la parte de Arriba izquierda, en la parte norte del Jlabel
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBusqueda.add(new JLabel("Buscar:"));
		txtBuscar = new JTextField(20);
		btnBuscar = new JButton("Buscar");
		
		panelBusqueda.add(txtBuscar);
		panelBusqueda.add(btnBuscar);
		
		add(panelBusqueda, BorderLayout.NORTH);
		
		
		String[] columnas = {"ISBN", "Título", "Autor", "Género", "Año","Disponibles"};
		modelo = new DefaultTableModel(columnas,0);
		
	}
	
}
