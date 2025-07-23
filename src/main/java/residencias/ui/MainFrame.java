package residencias.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Residencias UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Panel principal con layout en dos columnas
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel);

        // Panel izquierdo: Registro de estudiantes
        RegistroEstudiantes registroPanel = new RegistroEstudiantes();
        mainPanel.add(registroPanel);

        // Panel derecho: Gestión de cupos
        GestionCupos gestionCupos = new GestionCupos(registroPanel);
        mainPanel.add(gestionCupos);
    }
}
