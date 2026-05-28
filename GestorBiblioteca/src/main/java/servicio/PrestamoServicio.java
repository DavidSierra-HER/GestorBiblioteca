package servicio;

import java.time.LocalDate;
import java.util.List;

import dao.LibroDAOimp;
import dao.PrestamoDaoimp;
import modelo.Libro;
import modelo.Prestamo;

/**
 * Está es la clase principal de servicios de los Prestamos donde se comprueban los metodos.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class PrestamoServicio {

    private PrestamoDaoimp dao;
    private LibroDAOimp libroDAO;

    private static PrestamoServicio instance;

    public static synchronized PrestamoServicio getInstance() {
        if (instance == null) instance = new PrestamoServicio();
        return instance;
    }

    //en este caso necesitamos el de libro porque cambiamos la cantidad de unidades que disponemos al prestar un libro.
    private PrestamoServicio() {
        this.dao = new PrestamoDaoimp();
        this.libroDAO = new LibroDAOimp();
    }

    public List<Prestamo> obtenerTodos() { 
    	return dao.obtenerTodosLosPrestamos(); 
    	}
  
    public List<Prestamo> obtenerActivos() { 
    	return dao.buscarPrestamosActivos(); 
    	}
  
    public List<Prestamo> obtenerVencidos() { 
    	return dao.buscarPrestamosVencidos(); 
    	}
   
    public List<Prestamo> obtenerDevueltos() { 
    	return dao.buscarPrestamosDevueltos(); 
    	}

    // Registra préstamo si el libro tiene ejemplares disponibles,decrementa las unidades disponible en la base de datos y en caso de que no haya muestra error.
    public void registrar(Prestamo prestamo) {
        Libro libro = prestamo.getLibroPrestado();
       
        if (libro.getDisponibles() > 0) {
            libro.setDisponibles(libro.getDisponibles() - 1);
         
            libroDAO.modificarLibro(libro);
            dao.registrarPrestamo(prestamo);
        
        } else {
            System.err.println("El libro no tiene ejemplares disponibles.");
        }
    }

    // Devuelve el libro actualizando estado y fecha
    public void devolver(Prestamo prestamo) {
        prestamo.setEstado("DEVUELTO");
        
        prestamo.setFechaDevolucion(LocalDate.now());
        
        Libro libro = prestamo.getLibroPrestado();
        
        libro.setDisponibles(libro.getDisponibles() + 1);
        
        libroDAO.modificarLibro(libro);
        dao.actualizarEstado(prestamo);
    }

    // Comprueba si han pasado más de 20 días y lo marca como vencido
    public void comprobarVencimiento(Prestamo prestamo) {
    	LocalDate vencimiento = prestamo.getFechaPrestado().plusDays(20);
    	if(LocalDate.now().isAfter(vencimiento) && prestamo.getEstado().equals("ACTIVO")) {
            prestamo.setEstado("VENCIDO");
            dao.actualizarEstado(prestamo);
        }
    }

    //Elimina el registro del prestamo
    public void eliminar(int id) {
        Prestamo p = dao.obtenerPorId(id);
        if(p != null) {
            dao.eliminarPrestamo(id);
        } else {
            System.err.println("El préstamo con id " + id + " NO existe.");
        }
    }
}