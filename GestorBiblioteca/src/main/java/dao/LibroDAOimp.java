package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Libro;
import util.ConexionDB;

/**
 * Está es la clase principal de implementacion de los libros donde se introducen las sentencias SQL
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class LibroDAOimp implements LibroDAO{
	
	//parametros de la busqueda SQL en el apartado libro
	private final String OBTENER = "SELECT * FROM libro ORDER BY titulo;";
	private final String REGISTRARLIBRO = "INSERT INTO libro (ISBN, TITULO, AUTOR, GENERO, ANNO, EJEMPLARES_DISPONIBLES) VALUES (?, ?, ?, ?, ?, ?);";
	private final String ACTUALIZAR = "UPDATE libro SET ISBN = ?, TITULO = ?, AUTOR = ?, GENERO = ?, ANNO = ?, EJEMPLARES_DISPONIBLES = ? WHERE ISBN = ?;";
	private final String ELIMINAR = "DELETE FROM libro WHERE ISBN = ?;";
	private final String BUSCAR_POR_ISBN = "SELECT * FROM libro WHERE ISBN = ?;";
	private final String BUSCAR_POR_AUTOR = "SELECT * FROM libro WHERE AUTOR LIKE ?;";
	private final String BUSCAR_POR_TITULO = "SELECT * FROM libro WHERE TITULO LIKE ?;";
	private final String PURGARTABLA = "TRUNCATE TABLE libro;";

	
	//método para mostrar los libros
	@Override
	public List<Libro> obtenerLibros() {
		List<Libro> listaLibros= new ArrayList<Libro>();
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection()
				.prepareStatement(OBTENER)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {				
				Libro l = new Libro(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
				listaLibros.add(l);
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return listaLibros;
	}


	
	//metodo para modificar los libros
	@Override
	public boolean modificarLibro(Libro libro) {
		boolean actualizar = false;
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ACTUALIZAR)) {
			stmt.setString(1, libro.getIsbn());
			stmt.setString(2, libro.getTitulo());
			stmt.setString(3, libro.getAutor());
			stmt.setString(4, libro.getGenero());
			stmt.setString(5, libro.getAnno());
			stmt.setInt(6, libro.getDisponibles());			
			stmt.setString(7, libro.getIsbn());
			
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
	
	//método de registro de libros sigueindo los parametros de Libro	
	@Override
	public boolean registrarLibro(Libro libro) {
		boolean registrar = false;
		try (PreparedStatement pstmt = ConexionDB.getInstance().getConnection().prepareStatement(REGISTRARLIBRO)) {
			pstmt.setString(1, libro.getIsbn());
			pstmt.setString(2, libro.getTitulo());
			pstmt.setString(3, libro.getAutor());
			pstmt.setString(4, libro.getGenero());
			pstmt.setString(5, libro.getAnno());
			pstmt.setInt(6, libro.getDisponibles());	
			int row = pstmt.executeUpdate();			
			if(row == 1) {
				registrar = true;
			}
			System.out.println(registrar ? "Insertado correctamente" : "No se ha podido insertar...");
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}	
		return registrar;
	}
	
	//metodo pra eliminar libros de la biblioteca
	@Override
	public void eliminarLibro(String isbn) {
		try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(ELIMINAR)) {
			stmt.setString(1, isbn);
			
			int row = stmt.executeUpdate();
			System.out.println(row == 1 ? "Borrado con éxito" : "No se ha podido borrar...");
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		
	}

	
	//buscar por titulo
	@Override
	public List<Libro> buscarTitulo(String titulo) {
		
		 List<Libro> listaTitulo = new ArrayList<>();
		
		
		 
		 try(PreparedStatement stmt =ConexionDB.getInstance().getConnection().prepareStatement(BUSCAR_POR_TITULO)){
			 
			 stmt.setString(1,"%" + titulo + "%");
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 Libro l = new Libro(
						 rs.getString("ISBN"),
						 rs.getString("TITULO"),
						 rs.getString("AUTOR"),
						 rs.getString("GENERO"),
						 rs.getString("ANNO"),
						 rs.getInt("EJEMPLARES_DISPONIBLES")
						 );
				 listaTitulo.add(l);
			 } 
		       }catch (SQLException e) {
			        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			 }
		 
		 return listaTitulo;
	}

	
	//metodo para buscar por autor
	@Override
	public List<Libro>  buscarAutor(String autor) {
		 List<Libro> listaAutor = new ArrayList<>();
			
		 try(PreparedStatement stmt =ConexionDB.getInstance().getConnection().prepareStatement(BUSCAR_POR_AUTOR)){
			 
			 stmt.setString(1,"%" + autor + "%");
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 Libro l = new Libro(
						 rs.getString("ISBN"),
						 rs.getString("TITULO"),
						 rs.getString("AUTOR"),
						 rs.getString("GENERO"),
						 rs.getString("ANNO"),
						 rs.getInt("EJEMPLARES_DISPONIBLES")
						 );
				 listaAutor.add(l);
			 } 
		       }catch (SQLException e) {
			        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			 }
		 
		 return listaAutor;
		
		
	}

	
	//metodo para buscar por isbn
	@Override
	public Libro buscarISBN(String isbn) {
		
		    Libro libro = null;

		    try (PreparedStatement stmt = ConexionDB.getInstance()
		            .getConnection()
		            .prepareStatement(BUSCAR_POR_ISBN)) {

		        stmt.setString(1, isbn);

		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {
		            libro = new Libro(
		                rs.getString("ISBN"),
		                rs.getString("TITULO"),
		                rs.getString("AUTOR"),
		                rs.getString("GENERO"),
		                rs.getString("ANNO"),
		                rs.getInt("EJEMPLARES_DISPONIBLES")
		            );
		        }

		    } catch (SQLException e) {
		        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		    }

		    return libro;
}


	//purga total de la tabla para evitar duplicados al restaurar
	@Override
	public void purgarTabla() {
			try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(PURGARTABLA)) {
				stmt.executeUpdate();
				System.out.println("tabla purgada con éxito");
			} catch (SQLException e) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			}
	}
	
	//Obtiene el paginado de los Jtable
	@Override
	public List<Libro> obtenerPagina(int pagina, int tamPagina) {
	    List<Libro> lista = new ArrayList<>();
	    String sql = "SELECT * FROM libro ORDER BY TITULO LIMIT ? OFFSET ?";

	    try (PreparedStatement stmt = ConexionDB.getInstance().getConnection().prepareStatement(sql)) {
	        stmt.setInt(1, tamPagina);
	        stmt.setInt(2, pagina * tamPagina);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Libro l = new Libro(
	                rs.getString("ISBN"),
	                rs.getString("TITULO"),
	                rs.getString("AUTOR"),
	                rs.getString("GENERO"),
	                rs.getString("ANNO"),
	                rs.getInt("EJEMPLARES_DISPONIBLES")
	            );
	            lista.add(l);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	
}
