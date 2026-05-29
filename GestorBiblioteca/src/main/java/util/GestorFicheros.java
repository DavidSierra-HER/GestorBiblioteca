package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import dao.LibroDAOimp;
import dao.PrestamoDaoimp;
import dao.SocioDAOimp;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Socio;

/**
 * Está es la clase para guardar los elementos binarios con sus metodos
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class GestorFicheros implements Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LibroDAOimp libroDAO = new LibroDAOimp();
	private SocioDAOimp socioDAO = new SocioDAOimp();
	private PrestamoDaoimp prestamoDAO = new PrestamoDaoimp();

	
	
	//Cremos el objeto binario backup.bak donde guardamos todos los elementos de forma local 
	public void copiaSeguridad() {	
	try (ObjectOutputStream oos =
	        new ObjectOutputStream(
	            new BufferedOutputStream(
	                new FileOutputStream("backup.bak")))) {

	    oos.writeObject(libroDAO.obtenerLibros());
	    
	    oos.writeObject(socioDAO.obtenerSocio());

	    oos.writeObject(prestamoDAO.obtenerTodosLosPrestamos());
	    
	    System.out.println("Copia de seguridad realizada.");

	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}
	
	
	//Este metodo, hace varios elementos primero recupera el backup y los guarda en listas, 
	//después purgamos  las tablas para evitar duplicados y finalmente registrar de nuevo todos los libros
	//daba un error en los ois.read Type safety: Unchecked cast from Object to List<Socio> pero no deberia afectar
	
	@SuppressWarnings("unchecked")
	public void restaurarCopiaSeguridad() {	
	try (ObjectInputStream ois =
	        new ObjectInputStream(
	            new BufferedInputStream(
	                new FileInputStream("backup.bak")))) {

		List<Libro> libros = (List<Libro>) ois.readObject();
		List<Socio> socios = (List<Socio>) ois.readObject();
	    List<Prestamo> prestamos = (List<Prestamo>) ois.readObject();
	    
	    prestamoDAO.purgarTabla();
	    socioDAO.purgarTabla();
	    libroDAO.purgarTabla();
	    
	    for(Libro l : libros) {
	    	libroDAO.registrarLibro(l);
	    }for(Socio s : socios) {
	    	socioDAO.registrarSocio(s);
	    }for(Prestamo p : prestamos) {
	    	prestamoDAO.registrarPrestamo(p);
	    }
	    
	    
	    
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}
}
