package controlador;

import java.util.List;

import exception.LibroNoEncontradoException;
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

    // Devuelve la lista completa de libros
    public List<Libro> obtenerLibros() {
        return LibroServicio.getInstance().obtenerLibros();
    }

    // Busca y devuelve un libro por ISBN, lanza excepción si no existe
    public Libro obtenerISBN(String isbn) throws LibroNoEncontradoException {
        return LibroServicio.getInstance().obtenerISBN(isbn);
    }

    // Busca y devuelve un libro por autor, lanza excepción si no existe
    public Libro obtenerAutor(String autor) throws LibroNoEncontradoException {
        return LibroServicio.getInstance().obtenerAutor(autor);
    }

    // Busca y devuelve un libro por título, lanza excepción si no existe
    public Libro buscarTitulo(String titulo) throws LibroNoEncontradoException {
        return LibroServicio.getInstance().buscarTitulo(titulo);
    }

    // Registra un nuevo libro si el ISBN no está en el sistema
    public void registrarLibro(Libro libro) throws LibroNoEncontradoException {
        LibroServicio.getInstance().registrarLibro(libro);
    }

    // Actualiza los datos de un libro existente
    public void actualizar(Libro libro) throws LibroNoEncontradoException {
        LibroServicio.getInstance().actualizar(libro);
    }

    // Elimina un libro del sistema por ISBN
    public void eliminar(String isbn) throws LibroNoEncontradoException {
        LibroServicio.getInstance().eliminar(isbn);
    }
}
