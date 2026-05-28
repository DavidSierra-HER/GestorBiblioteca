package servicio;

import java.util.List;

import dao.LibroDAOimp;
import modelo.Libro;

/**
 * Está es la clase principal de servicios de los libros donde se comprueban los metodos.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class LibroServicio {
	
	private LibroDAOimp dao;
	
	// Singleton
    private static LibroServicio instance;	
    /**
    * devuelve la instancia del patrón singleton
    */
    public static synchronized LibroServicio getInstance() {
        if (instance == null) {
            instance = new LibroServicio();
        }
        return instance;
    }
	
    
    //impementamos el da dentro del servicio
    public LibroServicio() {
		this.dao = new LibroDAOimp();
	}
    
    
    //Obtenemos todos los libros 
    public List<Libro> obtenerLibros(){ 
    	return dao.obtenerLibros(); 
    }
	
    
    //buscamos por el ISBN en caso de que no exista salta un error
    public Libro obtenerISBN(String isbn){ 
    	List<Libro> libros = dao.obtenerLibros(); 
    	Libro l = null;
    	for (Libro elem : libros) {
    		if(elem.getIsbn() == isbn)
    			l = elem;
		}    	
    	if(l == null) {
    		System.err.println("El ISBN " + isbn + " NO existe.");
    	}
    	return l; 
    }
    
  //buscamos por el autor en caso de que no exista salta un error
    public Libro obtenerAutor(String autor){ 
    	List<Libro> libros = dao.obtenerLibros(); 
    	Libro l = null;
    	for (Libro elem : libros) {
    		if(elem.getAutor() == autor)
    			l = elem;
		}    	
    	if(l == null) {
    		System.err.println("El autor " + autor + " NO existe.");
    	}
    	return l; 
    }
    
  //buscamos por el titulo en caso de que no exista salta un error
    public Libro buscarTitulo(String titulo){ 
    	List<Libro> libros = dao.obtenerLibros(); 
    	Libro l = null;
    	for (Libro elem : libros) {
    		if(elem.getTitulo() == titulo)
    			l = elem;
		}    	
    	if(l == null) {
    		System.err.println("El titulo " + titulo + " NO existe.");
    	}
    	return l; 
    }
    
    //Registramos libro en caso de que exista salta un error
    public void registrarLibro(Libro libro) {
    	Libro l = obtenerISBN(libro.getIsbn());
    	if( l == null) {
    		dao.registrarLibro(libro);
    	} else {
    		System.err.println("El Libro con ISBN: " + libro.getIsbn() + " existe.");
    	}
    }
    
  //actualizamos libro en caso de que no exista salta un error
    public void actualizar(Libro libro) {
    	Libro l = obtenerISBN(libro.getIsbn());
    	if(l != null) {
    		dao.modificarLibro(libro);
    	} else {
    		System.err.println("El Libro con isbn " + libro.getIsbn() + " no existe.");
    	}
    }
    
    
  //eliminamos libro en caso de que no exista salta un error
    public Libro eliminar(String isbn) {
    	List<Libro> libros = dao.obtenerLibros(); 
    	Libro l = null;
    	for (Libro elem : libros) {
    		if(elem.getIsbn() == isbn)
    			dao.eliminarLibro(isbn);
		}    	
    	if(l == null) {
    		System.err.println("El ISBN " + isbn + " NO existe.");
    	}
    	return l; 
    }

}
