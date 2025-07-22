package residencias.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Residencias UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Panel principal con GridLayout horizontal
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel);

        // Panel izquierdo: Formulario
        RegistroEstudiantes registroPanel = new RegistroEstudiantes();
        mainPanel.add(registroPanel);

        // Panel derecho: Información general o estado
        JTextArea areaInfo = new JTextArea();
        areaInfo.setText("📚 Bienvenido al sistema de residencias.\n\nUtilice el formulario para registrar estudiantes y gestionar su asignación a habitaciones.");
        areaInfo.setWrapStyleWord(true);
        areaInfo.setLineWrap(true);
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaInfo.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(areaInfo);
        mainPanel.add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

