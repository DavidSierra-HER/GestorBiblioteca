package exception;


/**
 * excepciones personalizadas
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class DniInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DniInvalidoException() {
		super();
	}
	
	public DniInvalidoException(String mensaje) {
		super(mensaje);
	}
	
	public DniInvalidoException(Throwable causa) {
		super(causa);
	}

	public DniInvalidoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}


}
