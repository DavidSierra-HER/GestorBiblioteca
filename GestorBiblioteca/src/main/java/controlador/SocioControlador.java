package controlador;

import java.util.List;

import exception.DniInvalidoException;
import exception.SocioNoEncontradoException;
import modelo.Socio;
import servicio.SocioServicio;

/**
 * Gestiona las peticiones de la vista y las delega al servicio.
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */

public class SocioControlador {
    
    // Devuelve la lista completa de socios ordenada por nombre
    public List<Socio> obtener(){ 
        return SocioServicio.getInstance().obtener(); 
    }
        
    // Busca y devuelve un socio por su dni, lanza excepción si no existe
    public Socio obtenerSocio(String dni) throws SocioNoEncontradoException { 
        return SocioServicio.getInstance().obtenerSocio(dni); 
    }
        
    // Registra un nuevo socio si el DNI no está en el sistema
    public void registrar(Socio socio) throws DniInvalidoException, SocioNoEncontradoException {
        SocioServicio.getInstance().registrar(socio);
    }

    // Actualiza los datos de un socio existente
    public void actualizar(Socio socio) throws SocioNoEncontradoException {
        SocioServicio.getInstance().actualizar(socio);
    }

    // Elimina un Socio del sistema por Dni
    public void eliminar(String dni) throws SocioNoEncontradoException {
        SocioServicio.getInstance().eliminar(dni);
    }    
}
