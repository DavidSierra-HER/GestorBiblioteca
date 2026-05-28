package controlador;

import java.util.List;

import modelo.Prestamo;
import servicio.PrestamoServicio;

/**
 * Gestiona las peticiones de la vista y las delega al servicio.
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */


public class PrestamoControlador {
	
	// Devuelve la lista completa de prestamos ordenada por id
			public List<Prestamo> obtener(){ 
		    	return PrestamoServicio.getInstance().obtenerTodos(); 
		    }
			
			// Devuelve la lista de prestamos activos
			public List<Prestamo> buscarPrestamosActivos(){ 
		    	return PrestamoServicio.getInstance().obtenerActivos(); 
		    }
			
			// Devuelve la lista de prestamos vencidos
			public List<Prestamo> obtenerVencidos(){ 
		    	return PrestamoServicio.getInstance().obtenerVencidos(); 
		    }
			
			// Devuelve la lista de prestamos devueltos
			public List<Prestamo> obtenerDevueltos(){ 
		    	return PrestamoServicio.getInstance().obtenerDevueltos(); 
		    }
		    
			//Comprueba si el préstamo supera los 20 días y lo marca como vencido
		    public void comprobarVencimiento(Prestamo id){ 
		    	 PrestamoServicio.getInstance().comprobarVencimiento(id); 
		    }
		    
		    // Registra un nuevo Prestamo si el id no está en el sistema
		    public void registrar(Prestamo prestamo) {
		    	PrestamoServicio.getInstance().registrar(prestamo);
		    }

		    // Elimina un Prestamo del sistema por Id
		    public void eliminar(int id) {
		    	PrestamoServicio.getInstance().eliminar(id);
		    } 
		    
		 // Devuelve un Prestamo del sistema
		    public void devolver(Prestamo prestamo) {
		    	PrestamoServicio.getInstance().devolver(prestamo);
		    } 

}
