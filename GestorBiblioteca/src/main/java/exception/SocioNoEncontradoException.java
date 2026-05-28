package exception;


/**
 * excepciones personalizadas
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class SocioNoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SocioNoEncontradoException() {
		super();
	}
	
	public SocioNoEncontradoException(String mensaje) {
		super(mensaje);
	}
	
	public SocioNoEncontradoException(Throwable causa) {
		super(causa);
	}

	public SocioNoEncontradoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
