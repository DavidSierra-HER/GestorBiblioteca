package modelo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Está es la clase principal del objeto prestamo donde se reciben sus parametros
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class Prestamo {
	
	//atributos de la clase prestamo
	private String id;
	private String estado;
	private LocalDate fechaPrestado;
	private LocalDate devolucionEstimada;
	private LocalDate fechaDevolucion;
	private Libro libroPrestado;
	private Socio socioPrestamo;
	
	
	//constructor
	public Prestamo(String id, String estado, LocalDate fechaPrestado, LocalDate devolucionEstimada,
			LocalDate fechaDevolucion, Libro libroPrestado, Socio socioPrestamo) {
		super();
		this.id = id;
		this.estado = estado;
		this.fechaPrestado = fechaPrestado;
		this.devolucionEstimada = devolucionEstimada;
		this.fechaDevolucion = fechaDevolucion;
		this.libroPrestado = libroPrestado;
		this.socioPrestamo = socioPrestamo;
	}
	
	


	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	public LocalDate getFechaPrestado() {
		return fechaPrestado;
	}




	public void setFechaPrestado(LocalDate fechaPrestado) {
		this.fechaPrestado = fechaPrestado;
	}




	public LocalDate getDevolucionEstimada() {
		return devolucionEstimada;
	}




	public void setDevolucionEstimada(LocalDate devolucionEstimada) {
		this.devolucionEstimada = devolucionEstimada;
	}




	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}




	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}




	public Libro getLibroPrestado() {
		return libroPrestado;
	}




	public void setLibroPrestado(Libro libroPrestado) {
		this.libroPrestado = libroPrestado;
	}




	public Socio getSocioPrestamo() {
		return socioPrestamo;
	}




	public void setSocioPrestamo(Socio socioPrestamo) {
		this.socioPrestamo = socioPrestamo;
	}




	//hascode y equals
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		return Objects.equals(id, other.id);
	}



	//ToString donde trabajamos con los resultados del prestamo
	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", prestado=" + estado + ", fechaPrestado=" + fechaPrestado
				+ ", devolucionEstimada=" + devolucionEstimada + ", fechaDevolucion=" + fechaDevolucion
				+ ", libroPrestado=" + libroPrestado + ", socioPrestamo=" + socioPrestamo + "]";
	}
	
}
