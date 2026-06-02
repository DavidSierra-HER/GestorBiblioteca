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
	
	
	private static boolean offline = false;
	private static List<Socio> cacheSocios = new ArrayList<>();
	
	
	private final String OBTENER = "SELECT * FROM socio ORDER BY NOMBRE;";
	private final String REGISTRARSOCIO = "INSERT INTO socio (DNI, NOMBRE, DIRECCION, TLFN,ALTA) VALUES (?, ?, ?, ?, ?);";	
	private final String ACTUALIZAR = "UPDATE socio SET DNI = ?, NOMBRE = ?, DIRECCION = ?, TLFN = ?, ALTA = ? WHERE DNI = ?;";	
	private final String ELIMINAR = "DELETE FROM socio WHERE DNI = ?;";
	private final String BUSCAR_POR_DNI = "SELECT * FROM socio WHERE DNI = ?;";	
	private final String PURGARTABLA = "TRUNCATE TABLE socio;";
	
	
	//metodo para obtener todo el listado de los socios
	@Override
	public List<Socio> obtenerSocio() {
		
	    // Si estamos en modo offline devolvemos la lista cargada desde el backup
	    if (offline) {
	        return cacheSocios;
	    }

		
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

	
	//metodo de registro de los socios
	@Override
	public boolean registrarSocio(Socio socio) {
		
	    // En modo offline añadimos el socio a la lista en memoria
	    if (offline) {
	        cacheSocios.add(socio);
	        return true;
	    }

		
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

	
	//metodo de modificacion de los socios
	@Override
	public boolean modificarSocio(Socio socio) {
		
	    // En modo offline actualizamos el socio dentro de la lista en memoria
	    if (offline) {
	        for (int i = 0; i < cacheSocios.size(); i++) {
	            if (cacheSocios.get(i).getDni().equals(socio.getDni())) {
	                cacheSocios.set(i, socio);
	                return true;
	            }
	        }
	        return false;
	    }

		
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

	
	//metodo de eliminacion de socios
	@Override
	public void eliminarSocio(String dni) {
		
	    // En modo offline eliminamos el socio de la lista en memoria
	    if (offline) {
	        cacheSocios.removeIf(s -> s.getDni().equals(dni));
	        return;
	    }

		
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ELIMINAR)) {
			stmt.setString(1, dni);
			
			int row = stmt.executeUpdate();
			System.out.println(row == 1 ? "Borrado con éxito" : "No se ha podido borrar...");
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		
	}

	
	//busqueda por DNI el metodo 
	@Override
	public Socio buscarDNI(String dni) {
		
	    // En modo offline buscamos dentro de la lista cargada
	    if (offline) {
	        return cacheSocios.stream()
	                .filter(s -> s.getDni().equals(dni))
	                .findFirst()
	                .orElse(null);
	    }

		
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


	@Override
	public void purgarTabla() {
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(PURGARTABLA)) {
			stmt.executeUpdate();
			System.out.println("tabla purgada con éxito");
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
}
	
	//Obtiene el paginado de los JTable
	@Override
	public List<Socio> obtenerPagina(int pagina, int tamPagina) {
		
	    // En modo offline devolvemos un sublist manual
	    if (offline) {
	        int inicio = pagina * tamPagina;
	        int fin = Math.min(inicio + tamPagina, cacheSocios.size());
	        if (inicio >= cacheSocios.size()) return new ArrayList<>();
	        return cacheSocios.subList(inicio, fin);
	    }

		
	    List<Socio> lista = new ArrayList<>();
	    String sql = "SELECT * FROM socio ORDER BY NOMBRE LIMIT ? OFFSET ?";

	    try (PreparedStatement stmt = 
	         ConexionDB.getInstance().getConnection().prepareStatement(sql)) {

	        stmt.setInt(1, tamPagina);
	        stmt.setInt(2, pagina * tamPagina);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Socio s = new Socio(
	                rs.getString("DNI"),
	                rs.getString("NOMBRE"),
	                rs.getString("DIRECCION"),
	                rs.getString("TLFN"),
	                rs.getDate("ALTA").toLocalDate()
	            );
	            lista.add(s);
	        }

	    } catch (SQLException e) {
	        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    }

	    return lista;
	}

//	 Activa el modo offline y carga la lista de socios desde el backup.
	   public static void setModoOffline(List<Socio> lista) {
	        offline = true;
	        cacheSocios = lista;
	        System.out.println("SocioDAOimp funcionando en MODO OFFLINE. Socios cargados: " + cacheSocios.size());
	    }

}
