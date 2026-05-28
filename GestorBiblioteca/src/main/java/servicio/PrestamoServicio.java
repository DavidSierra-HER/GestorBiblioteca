package servicio;

import java.time.LocalDate;
import java.util.List;
import dao.LibroDAOimp;
import dao.PrestamoDaoimp;
import exception.LibroNoDisponibleException;
import exception.PrestamoVencidoException;
import modelo.Libro;
import modelo.Prestamo;

/**
 * Clase principal de servicios de los Prestamos donde se comprueban los metodos.
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */
public class PrestamoServicio {

    private PrestamoDaoimp dao;
    private LibroDAOimp libroDAO;
    private static PrestamoServicio instance;

    // Singleton
    public static synchronized PrestamoServicio getInstance() {
        if (instance == null) instance = new PrestamoServicio();
        return instance;
    }

    // en este caso necesitamos el de libro porque cambiamos la cantidad de unidades al prestar
    private PrestamoServicio() {
        this.dao = new PrestamoDaoimp();
        this.libroDAO = new LibroDAOimp();
    }

    // Obtenemos todos los prestamos
    public List<Prestamo> obtenerTodos() { 
    	return dao.obtenerTodosLosPrestamos(); 
    	}

    // Devuelve la lista de prestamos activos
    public List<Prestamo> obtenerActivos() {
    	return dao.buscarPrestamosActivos(); 
    	}

    // Devuelve la lista de prestamos vencidos
    public List<Prestamo> obtenerVencidos() {
    	return dao.buscarPrestamosVencidos(); 
    	}

    // Devuelve la lista de prestamos devueltos
    public List<Prestamo> obtenerDevueltos() {
    	return dao.buscarPrestamosDevueltos(); 
    	}

    // Registra préstamo si hay ejemplares disponibles, decrementa el contador y guarda en BD
    public void registrar(Prestamo prestamo) throws LibroNoDisponibleException {
        Libro libro = prestamo.getLibroPrestado();
        if(libro.getDisponibles() > 0) {
            libro.setDisponibles(libro.getDisponibles() - 1);
            libroDAO.modificarLibro(libro);
            dao.registrarPrestamo(prestamo);
        } else {
            throw new LibroNoDisponibleException("El libro " + libro.getTitulo() + " no tiene ejemplares disponibles.");
        }
    }

    // Devuelve el libro actualizando estado, fecha e incrementando ejemplares disponibles
    public void devolver(Prestamo prestamo) {
        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucion(LocalDate.now());
        Libro libro = prestamo.getLibroPrestado();
        libro.setDisponibles(libro.getDisponibles() + 1);
        libroDAO.modificarLibro(libro);
        dao.actualizarEstado(prestamo);
    }

    // Comprueba si han pasado más de 20 días y lo marca como vencido
    public void comprobarVencimiento(Prestamo prestamo) throws PrestamoVencidoException {
        LocalDate vencimiento = prestamo.getFechaPrestado().plusDays(20);
        if(LocalDate.now().isAfter(vencimiento) && prestamo.getEstado().equals("ACTIVO")) {
            prestamo.setEstado("VENCIDO");
            dao.actualizarEstado(prestamo);
            throw new PrestamoVencidoException("El préstamo " + prestamo.getId() + " está vencido.");
        }
    }

    // Elimina el registro del prestamo comprobando que existe previamente
    public void eliminar(int id) throws PrestamoVencidoException {
        Prestamo p = dao.obtenerPorId(id);
        if(p != null) {
            dao.eliminarPrestamo(id);
        } else {
            throw new PrestamoVencidoException("El préstamo con id " + id + " NO existe.");
        }
    }
}