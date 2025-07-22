package residencias.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Residencias UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel);

        // Panel izquierdo: Formulario
        RegistroEstudiantes registroPanel = new RegistroEstudiantes();
        mainPanel.add(registroPanel);

        // Panel derecho: Mockup info o contenido dinámico
        JTextArea mockupInfo = new JTextArea();
       mockupInfo.setText("Bienvenido al sistema. Use el menú izquierdo para gestionar estudiantes.");
        mockupInfo.setLineWrap(true);
        mockupInfo.setWrapStyleWord(true);
        mockupInfo.setEditable(false);
        mockupInfo.setMargin(new Insets(10, 10, 10, 10));
        mockupInfo.setFont(new Font("Serif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(mockupInfo);
        mainPanel.add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
