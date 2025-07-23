package residencias.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import residencias.logica.GestorEstudiantes;

public class MainFrame extends JFrame {

    private GestorEstudiantes gestor;

    public MainFrame() {
        this.gestor = new GestorEstudiantes();
        setTitle("Sistema de Residencias UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel);

        // Panel izquierdo: Formulario
        RegistroEstudiantes registroPanel = new RegistroEstudiantes(gestor);
        mainPanel.add(registroPanel);

        // Panel derecho: Información estática (Mockup)
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
