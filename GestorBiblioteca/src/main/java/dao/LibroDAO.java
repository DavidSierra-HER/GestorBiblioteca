package dao;

import java.util.List;
import modelo.Libro;

public interface LibroDAO {

	public List<Libro> obtenerLibros();
	public void modificarLibro(Libro libro);
	public void eliminarLibro(String isbn);
	public List<Libro> buscarTitulo(String titulo);
	public List<Libro> buscarAutor(String autor);
	public Libro buscarISBN(String isbn);
	
	
}
