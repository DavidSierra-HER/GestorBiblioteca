package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Prestamo;
import util.ConexionDB;

/**
 * Está es la clase principal de implementacion de los Prestamos.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class PrestamoDaoimp implements PrestamoDAO{
	
	//Objetos para implementar los metodos de Socio y Libro dentro de Prestamo
	private LibroDAOimp libroDAO = new LibroDAOimp();
	private SocioDAOimp socioDAO = new SocioDAOimp();
	
	
	//Métodos SQL
	private final String OBTENER = "SELECT * FROM prestamo ORDER BY ID;";
	private final String REGISTRARPRESTAMO = "INSERT INTO prestamo (ID, ESTADO, FECHA_PRESTAMO, DEVOLUCION_ESTIMADA,FECHA_DEVOLUCION, LIBRO_PRESTADO, SOCIO_PRESTADO) VALUES (?, ?, ?, ?, ?,?,?);";	
	private final String ELIMINAR = "DELETE FROM prestamo WHERE ID = ?;";
	private final String ACTUALIZAR = "UPDATE prestamo SET ID = ?, ESTADO = ?, FECHA_PRESTAMO = ?, DEVOLUCION_ESTIMADA = ?, FECHA_DEVOLUCION = ?,LIBRO_PRESTADO = ?, SOCIO_PRESTADO = ? WHERE ID = ?;";	
	private final String BUSCAR_POR_ID = "SELECT * FROM prestamo WHERE ID = ?;";	
	private final String BUSCAR_POR_ACTIVOS =  "SELECT * FROM prestamo WHERE FECHA_DEVOLUCION IS NULL AND DEVOLUCION_ESTIMADA >= CURDATE();";
    private final String BUSCAR_POR_VENCIDOS = "SELECT * FROM prestamo WHERE FECHA_DEVOLUCION IS NULL AND DEVOLUCION_ESTIMADA < CURDATE();";
    private final String BUSCAR_POR_DEVUELTOS = "SELECT * FROM prestamo WHERE FECHA_DEVOLUCION IS NOT NULL;";
    private final String PURGARTABLA = "TRUNCATE TABLE prestamo;";
	
	//Método del listado de prestamo
	@Override
	public List<Prestamo> obtenerTodosLosPrestamos() {
		List<Prestamo> listaPrestamo= new ArrayList<Prestamo>();
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection()
				.prepareStatement(OBTENER)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {				
				Prestamo l = new Prestamo(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(), rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null,libroDAO.buscarISBN(rs.getString(6)), socioDAO.buscarDNI(rs.getString(7)));
				listaPrestamo.add(l);
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return listaPrestamo;
	}

	
	//Método de registro de prestamo
	@Override
	public boolean registrarPrestamo(Prestamo prestamo) {
		boolean registrar = false;
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(REGISTRARPRESTAMO)) {
			stmt.setString(1, prestamo.getId());
			stmt.setString(2, prestamo.getEstado());
			stmt.setDate(3, java.sql.Date.valueOf(prestamo.getFechaPrestado()));
			stmt.setDate(4, java.sql.Date.valueOf(prestamo.getDevolucionEstimada()));
			//esto puede dar null por ende debe revisar para que no salte error
			if(prestamo.getFechaDevolucion() != null) {
			    stmt.setDate(5, java.sql.Date.valueOf(prestamo.getFechaDevolucion()));
			} else {
			    stmt.setNull(5, java.sql.Types.DATE);
			}
			
			stmt.setString(6, prestamo.getLibroPrestado().getIsbn());
			stmt.setString(7, prestamo.getSocioPrestamo().getDni());
			
			
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

	
	//Actualización del estado del Prestamo
	@Override
	public boolean actualizarEstado(Prestamo prestamo) {
		boolean actualizar = false;
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ACTUALIZAR)) {
			stmt.setString(1, prestamo.getId());
			stmt.setString(2, prestamo.getEstado());
			stmt.setDate(3, java.sql.Date.valueOf(prestamo.getFechaPrestado()));
			stmt.setDate(4, java.sql.Date.valueOf(prestamo.getDevolucionEstimada()));
			//esto puede dar null por ende debe revisar para que no salte error
			if(prestamo.getFechaDevolucion() != null) {
			    stmt.setDate(5, java.sql.Date.valueOf(prestamo.getFechaDevolucion()));
			} else {
			    stmt.setNull(5, java.sql.Types.DATE);
			}
			
			stmt.setString(6, prestamo.getLibroPrestado().getIsbn());
			stmt.setString(7, prestamo.getSocioPrestamo().getDni());
			stmt.setString(8, prestamo.getId());
			
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

	
	//Eliminar el prestamo
	@Override
	public void eliminarPrestamo(int id) {
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ELIMINAR)) {
			stmt.setInt(1, id);
			
			int row = stmt.executeUpdate();
			System.out.println(row == 1 ? "Borrado con éxito" : "No se ha podido borrar...");
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		
	}

	
	//busca de prestamo por ID
	@Override
	public Prestamo obtenerPorId(int id) {
		Prestamo prestamo = null;

		    try (PreparedStatement stmt = ConexionDB.getInstance()
		            .getConnection()
		            .prepareStatement(BUSCAR_POR_ID)) {

		        stmt.setInt(1, id);

		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {
		        	prestamo = new Prestamo(
		                rs.getString("ID"),
		                rs.getString("ESTADO"),
		                rs.getDate("FECHA_PRESTAMO").toLocalDate(),
		                rs.getDate("DEVOLUCION_ESTIMADA").toLocalDate(),
		                rs.getDate("FECHA_DEVOLUCION") != null ? 
		                	    rs.getDate("FECHA_DEVOLUCION").toLocalDate() : null,
		                libroDAO.buscarISBN(rs.getString("LIBRO_PRESTADO")),
		                socioDAO.buscarDNI(rs.getString("SOCIO_PRESTAMO"))
		            );
		        }

		    } catch (SQLException e) {
		        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		    }

		    return prestamo;
}

	//Listado de prestamos activos
	@Override
	public List<Prestamo> buscarPrestamosActivos() {
		List<Prestamo> listaPrestamo= new ArrayList<Prestamo>();
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection()
				.prepareStatement(BUSCAR_POR_ACTIVOS)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {				
				Prestamo l = new Prestamo(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(), rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null,libroDAO.buscarISBN(rs.getString(6)), socioDAO.buscarDNI(rs.getString(7)));
				listaPrestamo.add(l);
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return listaPrestamo;
	}

	//listado de prestamos vencidos
	@Override
	public List<Prestamo> buscarPrestamosVencidos() {
		List<Prestamo> listaPrestamo= new ArrayList<Prestamo>();
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection()
				.prepareStatement(BUSCAR_POR_VENCIDOS)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {				
				Prestamo l = new Prestamo(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(), rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null,libroDAO.buscarISBN(rs.getString(6)), socioDAO.buscarDNI(rs.getString(7)));
				listaPrestamo.add(l);
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return listaPrestamo;
	}

	
	//listado deprestamos devueltos
	@Override
	public List<Prestamo> buscarPrestamosDevueltos() {
		List<Prestamo> listaPrestamo= new ArrayList<Prestamo>();
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection()
				.prepareStatement(BUSCAR_POR_DEVUELTOS)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {				
				Prestamo l = new Prestamo(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(), rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null,libroDAO.buscarISBN(rs.getString(6)), socioDAO.buscarDNI(rs.getString(7)));
				listaPrestamo.add(l);
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return listaPrestamo;
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
	


}
