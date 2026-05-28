package servicio;

import java.util.List;
import dao.LibroDAOimp;
import exception.LibroNoEncontradoException;
import modelo.Libro;
import util.Validaciones;

/**
 * Clase principal de servicios de los libros donde se comprueban los metodos. 
 * con los trows personalizados
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */
public class LibroServicio {

    private LibroDAOimp dao;
    private static LibroServicio instance;

    // Singleton
    public static synchronized LibroServicio getInstance() {
        if (instance == null) instance = new LibroServicio();
        return instance;
    }

    // implementamos el dao dentro del servicio
    private LibroServicio() {
        this.dao = new LibroDAOimp();
    }

    // Obtenemos todos los libros
    public List<Libro> obtenerLibros() {
        return dao.obtenerLibros();
    }

    // Buscamos por ISBN, validamos formato y lanzamos excepción si no existe
    public Libro obtenerISBN(String isbn) throws LibroNoEncontradoException {
        if(!Validaciones.validarISBN(isbn)) {
            throw new LibroNoEncontradoException("El ISBN " + isbn + " no tiene formato válido.");
        }
        List<Libro> libros = dao.obtenerLibros();
        Libro l = null;
        for (Libro elem : libros) {
            if(elem.getIsbn().equals(isbn))
                l = elem;
        }
        if(l == null) {
            throw new LibroNoEncontradoException("El ISBN " + isbn + " NO existe.");
        }
        return l;
    }

    // Buscamos por autor, lanzamos excepción si no existe
    public Libro obtenerAutor(String autor) throws LibroNoEncontradoException {
        List<Libro> libros = dao.obtenerLibros();
        Libro l = null;
        for (Libro elem : libros) {
            if(elem.getAutor().equals(autor))
                l = elem;
        }
        if(l == null) {
            throw new LibroNoEncontradoException("El autor " + autor + " NO existe.");
        }
        return l;
    }

    // Buscamos por título, lanzamos excepción si no existe
    public Libro buscarTitulo(String titulo) throws LibroNoEncontradoException {
        List<Libro> libros = dao.obtenerLibros();
        Libro l = null;
        for (Libro elem : libros) {
            if(elem.getTitulo().equals(titulo))
                l = elem;
        }
        if(l == null) {
            throw new LibroNoEncontradoException("El titulo " + titulo + " NO existe.");
        }
        return l;
    }

    // Registramos libro validando ISBN y comprobando que no existe previamente
    public void registrarLibro(Libro libro) throws LibroNoEncontradoException {
        if(!Validaciones.validarISBN(libro.getIsbn())) {
            throw new LibroNoEncontradoException("El ISBN " + libro.getIsbn() + " no tiene formato válido.");
        }
        Libro l = null;
        try { l = obtenerISBN(libro.getIsbn()); } catch (LibroNoEncontradoException e) {}
        if(l == null) {
            dao.registrarLibro(libro);
        } else {
            throw new LibroNoEncontradoException("El Libro con ISBN: " + libro.getIsbn() + " ya existe.");
        }
    }

    // Actualizamos libro comprobando que existe previamente
    public void actualizar(Libro libro) throws LibroNoEncontradoException {
        Libro l = obtenerISBN(libro.getIsbn());
        if(l != null) {
            dao.modificarLibro(libro);
        } else {
            throw new LibroNoEncontradoException("El Libro con isbn " + libro.getIsbn() + " no existe.");
        }
    }

    // Eliminamos libro comprobando que existe previamente
    public void eliminar(String isbn) throws LibroNoEncontradoException {
        Libro l = obtenerISBN(isbn);
        if(l != null) {
            dao.eliminarLibro(isbn);
        } else {
            throw new LibroNoEncontradoException("El ISBN " + isbn + " NO existe.");
        }
    }
}
