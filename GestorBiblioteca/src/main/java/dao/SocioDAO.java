package dao;

import java.util.List;
import modelo.Socio;

/**
 * Está es la clase principal de interfaz de los Socios.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public interface SocioDAO {
	
	
	public List<Socio> obtenerSocio();
	public boolean registrarSocio(Socio socio);
	public boolean modificarSocio(Socio socio);
	public void eliminarSocio(String dni);
	public Socio buscarDNI(String dni);
	public void purgarTabla();
	public List<Socio> obtenerPagina(int pagina, int tamPagina);

}
