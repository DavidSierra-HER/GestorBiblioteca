package dao;

import java.util.List;
import modelo.Prestamo;

/**
 * Está es la clase principal de interfaz de los prestamos.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public interface PrestamoDAO {
	
	public List<Prestamo> obtenerTodosLosPrestamos();
	public boolean registrarPrestamo(Prestamo prestamo);
	public boolean actualizarEstado(Prestamo prestamo);
	public void eliminarPrestamo(int id);
	public Prestamo obtenerPorId(int id);
	public List<Prestamo> buscarPrestamosActivos();
	public List<Prestamo> buscarPrestamosVencidos();
	public List<Prestamo> buscarPrestamosDevueltos();
	public void purgarTabla();
	
}
