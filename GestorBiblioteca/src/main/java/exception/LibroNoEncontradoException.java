package exception;

/**
 * excepciones personalizadas
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class LibroNoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LibroNoEncontradoException() {
		super();
	}
	
	public LibroNoEncontradoException(String mensaje) {
		super(mensaje);
	}
	
	public LibroNoEncontradoException(Throwable causa) {
		super(causa);
	}

	public LibroNoEncontradoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
