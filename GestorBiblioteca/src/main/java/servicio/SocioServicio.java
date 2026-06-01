package servicio;

import java.util.List;
import dao.SocioDAOimp;
import exception.DniInvalidoException;
import exception.SocioNoEncontradoException;
import modelo.Socio;
import util.Validaciones;

/**
 * Clase principal de servicios de los Socios donde se comprueban los metodos.
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */
public class SocioServicio {

    private SocioDAOimp dao;
    private static SocioServicio instance;

    // Singleton
    public static synchronized SocioServicio getInstance() {
        if (instance == null) instance = new SocioServicio();
        return instance;
    }

    // implementamos el dao dentro de los servicios
    private SocioServicio() {
        this.dao = new SocioDAOimp();
    }

    // Obtenemos todos los socios
    public List<Socio> obtener() {
        return dao.obtenerSocio();
    }

    // Buscamos al Socio por DNI, lanzamos excepción si no existe
    public Socio obtenerSocio(String dni) throws SocioNoEncontradoException {
        List<Socio> socios = dao.obtenerSocio();
        Socio s = null;
        for (Socio elem : socios) {
            if(elem.getDni().equals(dni))
                s = elem;
        }
        if(s == null) {
            throw new SocioNoEncontradoException("El socio con dni " + dni + " NO existe.");
        }
        return s;
    }

    // Registro validando DNI y comprobando que no existe previamente
    public void registrar(Socio socio) throws DniInvalidoException, SocioNoEncontradoException {
        if(!Validaciones.validarDNI(socio.getDni())) {
            throw new DniInvalidoException("El DNI " + socio.getDni() + " no tiene formato válido.");
        }
        Socio s = null;
        try { s = obtenerSocio(socio.getDni()); } catch (SocioNoEncontradoException e) {}
        if(s == null) {
            dao.registrarSocio(socio);
        } else {
            throw new SocioNoEncontradoException("El Socio con dni " + socio.getDni() + " ya existe.");
        }
    }

    // Actualización comprobando que el socio existe previamente
    public void actualizar(Socio socio) throws SocioNoEncontradoException {
        Socio s = obtenerSocio(socio.getDni());
        if(s != null) {
            dao.modificarSocio(socio);
        } else {
            throw new SocioNoEncontradoException("El Socio con DNI " + socio.getDni() + " no existe.");
        }
    }

    // Eliminación comprobando que el socio existe previamente
    public void eliminar(String dni) throws SocioNoEncontradoException {
        Socio s = obtenerSocio(dni);
        if(s != null) {
            dao.eliminarSocio(dni);
        } else {
            throw new SocioNoEncontradoException("El DNI " + dni + " NO existe.");
        }
    }
    
    //obtiene las paginas de la Jtable
    public List<Socio> obtenerPagina(int pagina, int tamPagina) {
        return dao.obtenerPagina(pagina, tamPagina);
    }

}