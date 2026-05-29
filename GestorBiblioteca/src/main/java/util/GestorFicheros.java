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


public class GestorFicheros implements Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LibroDAOimp dao;
	private SocioDAOimp dao1;
	private PrestamoDaoimp dao2;

	
	
	
	public void copiaSeguridad() {	
	try (ObjectOutputStream oos =
	        new ObjectOutputStream(
	            new BufferedOutputStream(
	                new FileOutputStream("backup.bak")))) {

	    oos.writeObject(dao.obtenerLibros());
	    
	    oos.writeObject(dao1.obtenerSocio());

	    oos.writeObject(dao2.obtenerTodosLosPrestamos());
	    
	    System.out.println("Copia de seguridad realizada.");

	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}
	
	public void RestaurarCopiaSeguridad() {	
	try (ObjectInputStream ois =
	        new ObjectInputStream(
	            new BufferedInputStream(
	                new FileInputStream("backup.bak")))) {

		List<Libro> libros = (List<Libro>) ois.readObject();
		List<Socio> socios = (List<Socio>) ois.readObject();
	    List<Prestamo> prestamos = (List<Prestamo>) ois.readObject();
	    
	    dao = (LibroDAOimp) libros;
	    dao1 = (SocioDAOimp) socios;
	    dao2 = (PrestamoDaoimp) prestamos;
	    
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
