package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Está es la clase principal del objeto Socio donde se reciben sus parametros
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class Socio implements Serializable {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//atributos de la clase Socio
	private String dni;
	private String nombre;
	private String direccion;
	private String tlfn;
	private LocalDate alta;
	
	
	//Constructor
	public Socio(String dni, String nombre, String direccion, String tlfn, LocalDate alta) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tlfn = tlfn;
		this.alta = alta;
	}


	//Setters y getters
	public String getDni() {
		return dni;
	}



	public void setDni(String dni) {
		this.dni = dni;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direcion) {
		this.direccion = direcion;
	}



	public String getTlfn() {
		return tlfn;
	}



	public void setTlfn(String tlfn) {
		this.tlfn = tlfn;
	}



	public LocalDate getAlta() {
		return alta;
	}



	public void setAlta(LocalDate alta) {
		this.alta = alta;
	}



	//orientamos el hash y el equals sobre el dni que es donde vamos a poder identificar más facilmente
	
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Socio other = (Socio) obj;
		return Objects.equals(dni, other.dni);
	}


	//ToString
	@Override
	public String toString() {
		return "Socio [dni=" + dni + ", nombre=" + nombre + ", direccion=" + direccion + ", tlfn=" + tlfn + ", alta="
				+ alta + "]";
	}
	
}
