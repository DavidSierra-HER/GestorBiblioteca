package controlador;

import java.util.List;
import modelo.Libro;
import servicio.LibroServicio;

/**
 * Gestiona las peticiones de la vista y las delega al servicio.
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */

public class LibroControlador {
   
	
	
	// Devuelve la lista completa de libros ordenada por título
	public List<Libro> obtener(){ 
    	return LibroServicio.getInstance().obtenerLibros(); 
    }
    
	// Busca y devuelve un libro por su ISBN, null si no existe
    public Libro obtenerLibro(String isbn){ 
    	return LibroServicio.getInstance().obtenerISBN(isbn); 
    }
    
    // Registra un nuevo libro si el ISBN no está en el sistema
    public void registrar(Libro libro) {
    	LibroServicio.getInstance().registrarLibro(libro);
    }

    // Actualiza los datos de un libro existente
    public void actualizar(Libro libro) {
    	LibroServicio.getInstance().actualizar(libro);
    }
    // Elimina un libro del sistema por ISBN
    public void eliminar(String isbn) {
    	LibroServicio.getInstance().eliminar(isbn);
    }    

}