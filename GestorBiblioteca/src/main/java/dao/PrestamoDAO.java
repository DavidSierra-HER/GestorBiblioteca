package dao;

public interface PrestamoDAO {
	
	
	public void obtenerID();
	public void actualizarEstado();
	public void eliminarPrestamo();
	public void buscarTodosLosPrestamos();
	public void buscarPrestamosActivos();
	public void buscarPrestamosVencidos();
	public void buscarPrestamoDevueltos();
	
}
