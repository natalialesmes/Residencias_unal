package residencias.ui;

import residencias.estructuras.MinHeapEstudiantes;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private MinHeapEstudiantes heap;
    private JTextArea areaInfo;

    public MainFrame() {
        setTitle("Sistema de Residencias UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Inicializar estructura de datos
        heap = new MinHeapEstudiantes();

        // Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel);

        // Panel izquierdo: formulario de registro
        RegistroEstudiantes registroPanel = new RegistroEstudiantes(heap, this);
        mainPanel.add(registroPanel);

        // Panel derecho: mostrar contenido del heap
        areaInfo = new JTextArea();
        areaInfo.setText("Bienvenido al sistema. Registre estudiantes a la izquierda.");
        areaInfo.setLineWrap(true);
        areaInfo.setWrapStyleWord(true);
        areaInfo.setEditable(false);
        areaInfo.setMargin(new Insets(10, 10, 10, 10));
        areaInfo.setFont(new Font("Serif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(areaInfo);
        mainPanel.add(scrollPane);
    }

    public void actualizarHeapInfo() {
        areaInfo.setText("Estado actual del heap:\n" + heap.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

