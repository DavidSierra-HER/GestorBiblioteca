package dao;

import java.util.List;
import modelo.Libro;

/**
 * Está es la clase de interfaz de los DAO de Libro
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public interface LibroDAO {

	public List<Libro> obtenerLibros();
	public boolean registrarLibro(Libro libro);
	public boolean modificarLibro(Libro libro);
	public void eliminarLibro(String isbn);
	public List<Libro> buscarTitulo(String titulo);
	public List<Libro> buscarAutor(String autor);
	public Libro buscarISBN(String isbn);
	public void purgarTabla();
	public List<Libro> obtenerPagina(int pagina, int tamPagina);
	
}
