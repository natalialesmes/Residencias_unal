package residencias.ui;

import javax.swing.*;
import java.awt.*;

public class RegistroEstudiantes extends JPanel {

    private JTextField idField, nombreField, puntajeField, cuposField;
    private JButton registrarBtn, consultarBtn, modificarBtn, eliminarBtn;
    private JButton asignarBtn, listaBtn, asignadosBtn, noAsignadosBtn;

    public RegistroEstudiantes() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(198, 226, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Bienvenidos al sistema de residencias UNAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titulo);
        add(Box.createVerticalStrut(10));

        // Sección de Registro
        JPanel registroPanel = crearRegistroPanel();
        add(registroPanel);

        // Sección disponibilidad
        JPanel disponibilidadPanel = crearDisponibilidadPanel();
        add(disponibilidadPanel);

        // Visualización de datos
        JPanel visualizacionPanel = crearVisualizacionPanel();
        add(visualizacionPanel);

        // Mensaje final
        JLabel gracias = new JLabel("¡¡ Gracias, vuelva pronto !!");
        gracias.setAlignmentX(Component.CENTER_ALIGNMENT);
        gracias.setFont(new Font("Arial", Font.ITALIC, 12));
        add(Box.createVerticalStrut(10));
        add(gracias);
    }

    private JPanel crearRegistroPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder("Registro"));

        idField = new JTextField();
        nombreField = new JTextField();
        puntajeField = new JTextField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Puntaje:"));
        panel.add(puntajeField);

        registrarBtn = new JButton("Registrar");
        consultarBtn = new JButton("Consultar");

        JPanel btnPanel1 = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel1.setOpaque(false);
        btnPanel1.add(registrarBtn);
        btnPanel1.add(consultarBtn);

        modificarBtn = new JButton("Modificar");
        eliminarBtn = new JButton("Eliminar");

        JPanel btnPanel2 = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel2.setOpaque(false);
        btnPanel2.add(modificarBtn);
        btnPanel2.add(eliminarBtn);

        panel.add(btnPanel1);
        panel.add(btnPanel2);

        return panel;
    }

    private JPanel crearDisponibilidadPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Disponibilidad"));

        cuposField = new JTextField(5);
        asignarBtn = new JButton("Asignar");

        panel.add(new JLabel("Cupos disponibles:"));
        panel.add(cuposField);
        panel.add(asignarBtn);

        return panel;
    }

    private JPanel crearVisualizacionPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Visualización de datos"));

        listaBtn = new JButton("Lista por puntaje");
        asignadosBtn = new JButton("Asignados");
        noAsignadosBtn = new JButton("No asignados");

        panel.add(listaBtn);
        panel.add(asignadosBtn);
        panel.add(noAsignadosBtn);

        return panel;
    }
}
