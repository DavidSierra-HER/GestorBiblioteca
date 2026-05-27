package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Socio;
import util.ConexionDB;

/**
 * Está es la clase principal de implementacion de los Socios con sus metodos y llamadas SQL.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */



public class SocioDAOimp implements SocioDAO{
	
	private final String OBTENER = "SELECT * FROM SOCIO ORDER BY NOMBRE;";
	private final String REGISTRARSOCIO = "INSERT INTO SOCIO (DNI, NOMBRE, DIRECCION, TLFN,ALTA) VALUES (?, ?, ?, ?, ?);";	
	private final String ACTUALIZAR = "UPDATE SOCIO SET DNI = ?, NOMBRE = ?, DIRECCION = ?, TLFN = ?, ALTA = ? WHERE DNI = ?;";	
	private final String ELIMINAR = "DELETE FROM SOCIO WHERE DNI = ?;";
	private final String BUSCAR_POR_DNI = "SELECT * FROM SOCIO WHERE DNI = ?;";	
	
	@Override
	public List<Socio> obtenerSocio() {
		List<Socio> listaSocio= new ArrayList<Socio>();
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection()
				.prepareStatement(OBTENER)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {				
				Socio s = new Socio(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5).toLocalDate());
				listaSocio.add(s);
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return listaSocio;
	}

	@Override
	public boolean registrarSocio(Socio socio) {
		boolean registrar = false;
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(REGISTRARSOCIO)) {
			stmt.setString(1, socio.getDni());
			stmt.setString(2, socio.getNombre());
			stmt.setString(3, socio.getDireccion());
			stmt.setString(4, socio.getTlfn());
			stmt.setDate(5, java.sql.Date.valueOf(socio.getAlta()));
			
			int row = stmt.executeUpdate();			
			if(row == 1) {
				registrar = true;
			}
			System.out.println(registrar ? "Registrado con éxito" : "No se ha podido registar...");

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return registrar;
	}

	@Override
	public boolean modificarSocio(Socio socio) {
		boolean actualizar = false;
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ACTUALIZAR)) {
			stmt.setString(1, socio.getDni());
			stmt.setString(2, socio.getNombre());
			stmt.setString(3, socio.getDireccion());
			stmt.setString(4, socio.getTlfn());
			stmt.setDate(5, java.sql.Date.valueOf(socio.getAlta()));
			stmt.setString(6, socio.getDni());
			
			int row = stmt.executeUpdate();			
			if(row == 1) {
				actualizar = true;
			}
			System.out.println(actualizar ? "Actualizado con éxito" : "No se ha podido actualizar...");

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return actualizar;
	}

	@Override
	public void eliminarSocio(String dni) {
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ELIMINAR)) {
			stmt.setString(1, dni);
			
			int row = stmt.executeUpdate();
			System.out.println(row == 1 ? "Borrado con éxito" : "No se ha podido borrar...");
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		
	}

	@Override
	public Socio buscarDNI(String dni) {
		 Socio socio = null;

		    try (PreparedStatement stmt = ConexionDB.getInstance()
		            .getConnection()
		            .prepareStatement(BUSCAR_POR_DNI)) {

		        stmt.setString(1, dni);

		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {
		        	socio = new Socio(
		                rs.getString("DNI"),
		                rs.getString("NOMBRE"),
		                rs.getString("DIRECCION"),
		                rs.getString("TLFN"),
		                rs.getDate("ALTA").toLocalDate()
		            );
		        }

		    } catch (SQLException e) {
		        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		    }

		    return socio;
}
	

}
