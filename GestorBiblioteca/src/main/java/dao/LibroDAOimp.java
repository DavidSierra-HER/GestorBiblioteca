package dao;

import java.util.List;

import modelo.Libro;

public class LibroDAOimp implements LibroDAO{
	
	//parametros de la busqueda SQL en el apartado libro
	private final String OBTENER = "SELECT * FROM LIBRO ORDER BY TITULO;";
	private final String REGISTRARLIBRO = "INSERT INTO LIBRO (ISBN, TITULO, AUTOR, GENERO, ANNO,EJEMPLARES_DISPONIBLES) VALUES (?, ?, ?, ?, ?,?);";	
	private final String ACTUALIZAR = "UPDATE LIBRO SET ISBN = ?, TITULO = ?, AUTOR = ?, GENERO = ?, ANNO = ?, EJEMPLARES_DISPONIBLES= ? WHERE ISBN = ?;";	
	private final String ELIMINAR = "DELETE FROM LIBRO WHERE ISBN = ?;";	
	private final String BUSCAR_POR_ISBN = "SELECT * FROM LIBRO WHERE ISBN = ?;";	
	private final String BUSCAR_POR_TITULO = "SELECT * FROM LIBRO WHERE TITULO = ?;";	
	private final String BUSCAR_POR_AUTOR = "SELECT * FROM LIBRO WHERE AUTOR = ?;";	
	
	@Override
	public List<Libro> obtenerLibros() {
		
		return null;
	}


	@Override
	public void modificarLibro(Libro libro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarLibro(String isbn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Libro>  buscarTitulo(String titulo) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Libro>  buscarAutor(String autor) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Libro buscarISBN(String isbn) {
		
		return null;
		// TODO Auto-generated method stub
		
	}

	
}
