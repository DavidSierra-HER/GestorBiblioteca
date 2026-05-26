package dao;

public interface PrestamoDAO {
	
	public void guardarPrestamo();
	public void obtenerID();
	public void actualizarEstado();
	public void eliminarPrestamo();
	public void buscarTodosLosPrestamos();
	public void buscarPrestamosActivos();
	public void buscarPrestamosVencidos();
	

}
