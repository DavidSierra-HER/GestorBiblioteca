package exception;


/**
 * excepciones personalizadas
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class LibroNoDisponibleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public LibroNoDisponibleException() {
		super();
	}
	
	public LibroNoDisponibleException(String mensaje) {
		super(mensaje);
	}
	
	public LibroNoDisponibleException(Throwable causa) {
		super(causa);
	}

	public LibroNoDisponibleException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
