package servicio;

import java.util.List;

import dao.SocioDAOimp;
import modelo.Socio;

/**
 * Está es la clase principal de servicios de los Socios donde se comprueban los metodos.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class SocioServicio {
	
	private SocioDAOimp dao;
	
	
	// Singleton
    private static SocioServicio instance;	
    /**
    * devuelve la instancia del patrón singleton
    */
    public static synchronized SocioServicio getInstance() {
        if (instance == null) {
            instance = new SocioServicio();
        }
        return instance;
    }
    
    //se implemnta el dao dentro de los servicios
    private SocioServicio() {
		this.dao = new SocioDAOimp();
	}
    
    //se lanza la obtención de todos los socios
    public List<Socio> obtener(){ 
    	return dao.obtenerSocio(); 
    }
    
    //Se busca al Socio por DNI
    public Socio obtenerSocio(String dni){ 
    	List<Socio> socios = dao.obtenerSocio();
    	Socio s = null;
    	for (Socio elem : socios) {
    		if(elem.getDni().equals(dni))
    			s = elem;
		}    	
    	if(s == null) {
    		System.err.println("El socio con dni " + dni + " NO existe.");
    	}
    	return s; 
    }
    
    //Registro de los socios en el sistema
    public void registrar(Socio socio) {
    	Socio s = obtenerSocio(socio.getDni());
    	if(s == null) {
    		dao.registrarSocio(socio);
    	} else {
    		System.err.println("El Socio con dni " + socio.getDni() + " existe.");
    	}
    }
    
    //Actualización de los datos del socio
    public void actualizar(Socio socio) {
    	Socio s = obtenerSocio(socio.getDni());
    	if(s != null) {
    		dao.modificarSocio(socio);
    	} else {
    		System.err.println("El Socio con DNI " + socio.getDni() + " no existe.");
    	}
    }
    
    //Eliminación del Socio
    public void eliminar(String dni) {
    	Socio s = obtenerSocio(dni);
	    if(s != null) {
	        dao.eliminarSocio(dni);
	    } else {
	        System.err.println("El DNI " + dni + " NO existe.");
	    }
	}

}
