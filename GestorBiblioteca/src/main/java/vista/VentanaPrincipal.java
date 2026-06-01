package vista;

import java.awt.*;
import javax.swing.*;

import util.GestorFicheros;

/**
 * Está es la clase principal de vista, la ventana principal del proyecto
 * 
 * @author  David
 * @version 1.0
 * @since   2026-05-26
 */


public class VentanaPrincipal {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel panelPrincipal;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                VentanaPrincipal window = new VentanaPrincipal();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public VentanaPrincipal() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestor Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // CardLayout para cambiar entre paneles.
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        // Creamos los paneles
        PanelLibro panelLibro = new PanelLibro(() -> mostrar("inicio"));
        PanelSocio panelSocio = new PanelSocio(() -> mostrar("inicio"));
        PanelPrestamo panelPrestamo = new PanelPrestamo(() -> mostrar("inicio"));

        // Pantalla de inicio con los tres botones
        PanelInicio panelInicio = new PanelInicio(
            () -> mostrar("libros"),
            () -> mostrar("socios"),
            () -> mostrar("prestamos")
        );

        // Añadimos todos al CardLayout con su nombre
        panelPrincipal.add(panelInicio, "inicio");
        panelPrincipal.add(panelLibro, "libros");
        panelPrincipal.add(panelSocio, "socios");
        panelPrincipal.add(panelPrestamo, "prestamos");

        frame.setContentPane(panelPrincipal);

        // Mostramos el inicio primero
        mostrar("inicio");
        
     // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);

        // Opción copia de seguridad
        JMenuItem itemCopia = new JMenuItem("Copia de seguridad");
        menuArchivo.add(itemCopia);

        // Opción restaurar
        JMenuItem itemRestaurar = new JMenuItem("Restaurar copia");
        menuArchivo.add(itemRestaurar);

        // Separador
        menuArchivo.addSeparator();

        // Opción salir
        JMenuItem itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemSalir);

        // ActionListeners
        GestorFicheros gestor = new GestorFicheros();

        itemCopia.addActionListener(e -> {
            gestor.copiaSeguridad();
            JOptionPane.showMessageDialog(frame, "Copia de seguridad realizada correctamente.");
        });

        itemRestaurar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(frame,
                "¿Está seguro? Esto sobreescribirá los datos actuales.", 
                "Confirmar restauración", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                gestor.restaurarCopiaSeguridad();
                JOptionPane.showMessageDialog(frame, "Copia restaurada correctamente.");
            }
        });

        itemSalir.addActionListener(e -> System.exit(0));
        
    }

    // Cambia el panel visible
    private void mostrar(String nombre) {
        cardLayout.show(panelPrincipal, nombre);
    }
}