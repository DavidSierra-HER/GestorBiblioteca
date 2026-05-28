package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Se cubren las expresiones que deben cumplir los usuarios para que sea valido el isbn o dni
 *
 * @author David
 * @version 1.0
 * @since 2026-05-26
 */

public class Validaciones {
	
	//validación de DNI español: 8 números + letra mayuscula
	public static boolean validarDNI(String dni) {
		Pattern patron = Pattern.compile("[0-9]{8}[A-Z]{1}");
		Matcher matcher = patron.matcher(dni);
		return matcher.matches();
	}
	
	 // Valida ISBN-13: 13 dígitos, puede contener guiones
    public static boolean validarISBN(String isbn) {
        Pattern patron = Pattern.compile("[0-9]{3}-?[0-9]+-?[0-9]+-?[0-9]+-?[0-9]");
        Matcher matcher = patron.matcher(isbn);
        return matcher.matches();
    }

}
