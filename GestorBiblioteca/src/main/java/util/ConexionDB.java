package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Está es la clase principal para la conexion a la db.properties usando
 * la clase properties e inputstream para facilitar correciones en el fúturo.
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */

public class ConexionDB {
	
	//parametros para conectarlo a la db.properties y no hardcodearlo
	Properties propiedades = new Properties();
	InputStream input = getClass().getClassLoader().getResourceAsStream("config/db.properties");
	
	String url = propiedades.getProperty("db.url");
	String usuario = propiedades.getProperty("db.usuario");
	String contrasena = propiedades.getProperty("db.password");
	
	private Connection conexion = null;
	
	
    // Singleton
    private static ConexionDB instance;
    
    /**
     * devuelve la instancia del patrón singleton
     */
    public static synchronized ConexionDB getInstance() {
        if (instance == null) {
            instance = new ConexionDB();
        }
        return instance;
    }
	
    // constructor de la conexion con los atributos de la db.properties
    private ConexionDB() {
       
    	try {
    		//objeto donde almacenamos las claves y su valor respectivo
            Properties propiedades = new Properties();
            //donde están esos valores
            InputStream input = getClass().getClassLoader()
                               .getResourceAsStream("config/db.properties");
            //se cargan
            propiedades.load(input);
            
            //atributos para el drive manager
            String url = propiedades.getProperty("db.url");
            String usuario = propiedades.getProperty("db.usuario");
            String contrasena = propiedades.getProperty("db.password");
           
            //lanzamos conexión
            this.conexion = DriverManager.getConnection(url, usuario, contrasena);
            
            //errores como en el ejemplo singleton de clase
        } catch (SQLException e) {
            System.err.println("Error al crear la conexión con la BBDD...");
            System.err.printf("Mensaje   : %s %n", e.getMessage());
            System.err.printf("SQL estado: %s %n", e.getSQLState());
            System.err.printf("Cód error : %s %n", e.getErrorCode());
        } catch (Exception e) {
            System.err.println("Error al cargar db.properties...");
            System.err.println(e.getMessage());
        }
    }
    
    // getters
    public Connection getConnection() {
        return conexion;
    }

    // otros métodos
    public void destroyConnection() {
        try {
            //System.out.println("Cerrando conexión con la BBDD...");
            this.conexion.close();
            //System.out.println("Conexión con la BBDD cerrada!!");
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión con la BBDD...");
            System.err.printf("Mensaje   : %s %n", e.getMessage());
            System.err.printf("SQL estado: %s %n", e.getSQLState());
            System.err.printf("Cód error : %s %n", e.getErrorCode());
        } finally {
            instance = null;
        }

    }

}
