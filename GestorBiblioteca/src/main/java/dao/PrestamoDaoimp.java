package dao;

import java.util.List;

import modelo.Prestamo;

/**
 * Está es la clase principal de implementacion de los Prestamos.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class PrestamoDaoimp implements PrestamoDAO{

	@Override
	public List<Prestamo> obtenerTodosLosPrestamos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean registrarPrestamo(Prestamo prestamo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean actualizarEstado(Prestamo prestamo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eliminarPrestamo(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Prestamo obtenerPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prestamo> buscarPrestamosActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prestamo> buscarPrestamosVencidos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prestamo> buscarPrestamosDevueltos() {
		// TODO Auto-generated method stub
		return null;
	}

	


}
