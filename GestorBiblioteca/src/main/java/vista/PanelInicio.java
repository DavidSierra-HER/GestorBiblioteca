package vista;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelInicio(Runnable irLibros, Runnable irSocios, Runnable irPrestamos) {
        setLayout(new BorderLayout());

        // Título.
        JLabel titulo = new JLabel("Gestor Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));
        add(titulo, BorderLayout.NORTH);

        // Subtítulo
        JLabel subtitulo = new JLabel("Biblioteca Municipal de Ciudad Rodrigo", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        // Panel central con botones que llevan a los 
        JPanel panelCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.ipady = 30;

        JButton btnLibros = new JButton("Gestión de Libros");
        JButton btnSocios = new JButton("Gestión de Socios");
        JButton btnPrestamos = new JButton("Gestión de Préstamos");

        btnLibros.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSocios.setFont(new Font("Arial", Font.PLAIN, 18));
        btnPrestamos.setFont(new Font("Arial", Font.PLAIN, 18));

        gbc.gridy = 0;
        panelCentro.add(subtitulo, gbc);
        gbc.gridy = 1;
        panelCentro.add(btnLibros, gbc);
        gbc.gridy = 2;
        panelCentro.add(btnSocios, gbc);
        gbc.gridy = 3;
        panelCentro.add(btnPrestamos, gbc);

        add(panelCentro, BorderLayout.CENTER);

        // Eventos 
        btnLibros.addActionListener(e -> irLibros.run());
        btnSocios.addActionListener(e -> irSocios.run());
        btnPrestamos.addActionListener(e -> irPrestamos.run());
    }
}