package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Está es la clase principal del objeto libro donde se reciben sus parametros
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class Libro implements Serializable {
	

	
//	atributos de la clase libro.
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String isbn;
	private String titulo;
	private String autor;
	private String genero;
	private String anno;
	private int disponibles;
	
	
	
	//constructor
	public Libro(String isbn, String titulo, String autor, String genero, String anno, int disponibles) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.anno = anno;
		this.disponibles = disponibles;
	}



	//getters y setters

	public String getIsbn() {
		return isbn;
	}




	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}




	public String getTitulo() {
		return titulo;
	}




	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}




	public String getAutor() {
		return autor;
	}




	public void setAutor(String autor) {
		this.autor = autor;
	}




	public String getGenero() {
		return genero;
	}




	public void setGenero(String genero) {
		this.genero = genero;
	}




	public String getAnno() {
		return anno;
	}




	public void setAnno(String anno) {
		this.anno = anno;
	}




	public int getDisponibles() {
		return disponibles;
	}




	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}



	//orientamos el hash y el equals sobre el ibn que es donde vamos a poder identificar más facilmente

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(isbn, other.isbn);
	}



	//ToString 
	@Override
	public String toString() {
		return "Libro [isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + ", año="
				+ anno + ", disponibles=" + disponibles + "]";
	}
}