package residencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import residencias.estructuras.Estudiante; // Import the Estudiante class

public class GestionCupos extends JPanel {

    private JTextField campoCupos;
    private JButton btnAsignar;
    private RegistroEstudiantes registroPanel;

    private JTextField campoBuscarId;
    private JButton btnBuscar;
    private JTextArea areaDatosEstudiante;
    private JTextField campoNuevoPuntaje;
    private JButton btnModificarPuntaje;
    private JButton btnEliminarEstudiante;

    public GestionCupos(RegistroEstudiantes registroPanel) {
        this.registroPanel = registroPanel;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Gestión de Cupos y Estudiantes"));
        setBackground(Color.WHITE);


        JPanel panelCupos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelCupos.setBorder(BorderFactory.createTitledBorder("Gestión de Cupos"));
        panelCupos.setBackground(Color.WHITE);

        campoCupos = new JTextField(5);
        panelCupos.add(new JLabel("Número de cupos:"));
        panelCupos.add(campoCupos);

        btnAsignar = new JButton("Asignar Residencias");
        btnAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarCupos();
            }
        });
        panelCupos.add(btnAsignar);

        JPanel panelEstudiantes = new JPanel(new BorderLayout(10, 10));
        panelEstudiantes.setBorder(BorderFactory.createTitledBorder("Gestión de Estudiantes"));
        panelEstudiantes.setBackground(Color.WHITE);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        campoBuscarId = new JTextField(15);
        btnBuscar = new JButton("Buscar Estudiante");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEstudiante();
            }
        });
        panelBusqueda.add(new JLabel("ID del Estudiante:"));
        panelBusqueda.add(campoBuscarId);
        panelBusqueda.add(btnBuscar);

        areaDatosEstudiante = new JTextArea(5, 30);
        areaDatosEstudiante.setEditable(false);
        areaDatosEstudiante.setMargin(new Insets(5, 5, 5, 5));
        areaDatosEstudiante.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollDatosEstudiante = new JScrollPane(areaDatosEstudiante);

        JPanel panelModificarEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        campoNuevoPuntaje = new JTextField(5);
        btnModificarPuntaje = new JButton("Modificar Puntaje");
        btnModificarPuntaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPuntaje();
            }
        });
        btnEliminarEstudiante = new JButton("Eliminar Estudiante");
        btnEliminarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEstudiante();
            }
        });
        panelModificarEliminar.add(new JLabel("Nuevo Puntaje:"));
        panelModificarEliminar.add(campoNuevoPuntaje);
        panelModificarEliminar.add(btnModificarPuntaje);
        panelModificarEliminar.add(btnEliminarEstudiante);

        panelEstudiantes.add(panelBusqueda, BorderLayout.NORTH);
        panelEstudiantes.add(scrollDatosEstudiante, BorderLayout.CENTER);
        panelEstudiantes.add(panelModificarEliminar, BorderLayout.SOUTH);


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(panelCupos);
        add(Box.createVerticalStrut(10));
        add(panelEstudiantes);
        add(Box.createVerticalGlue());
    }

    private void asignarCupos() {
        try {
            int cupos = Integer.parseInt(campoCupos.getText().trim());
            registroPanel.getGestor().asignarcupo(cupos);
            int asignados = registroPanel.getGestor().asignarRes();

            StringBuilder resultText = new StringBuilder();
            resultText.append("Residencias asignadas: ").append(asignados).append(" de ").append(cupos).append(" cupos disponibles.\n\n");
            resultText.append("Estudiantes asignados:\n");

            for (var est : registroPanel.getGestor().asignados()) {
                resultText.append("- ").append(est.getNombre()).append(" (ID: ").append(est.getId()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, resultText.toString(), "Asignación de Residencias", JOptionPane.INFORMATION_MESSAGE);

            registroPanel.actualizarTexto();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para los cupos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEstudiante() {
        String id = campoBuscarId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estudiante estudiante = registroPanel.getGestor().consultar(id);
        if (estudiante != null) {
            areaDatosEstudiante.setText("ID: " + estudiante.getId() + "\n");
            areaDatosEstudiante.append("Nombre: " + estudiante.getNombre() + "\n");
            areaDatosEstudiante.append("Puntaje: " + estudiante.getPuntaje() + "\n");
            areaDatosEstudiante.append("Residente: " + (estudiante.getResidente() ? "Sí" : "No") + "\n");
            campoNuevoPuntaje.setText(String.valueOf(estudiante.getPuntaje()));
        } else {
            areaDatosEstudiante.setText("No se encontró ningún estudiante con el ID: " + id);
            campoNuevoPuntaje.setText("");
        }
    }

    private void modificarPuntaje() {
        String id = campoBuscarId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, busque un estudiante primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int newPuntaje = Integer.parseInt(campoNuevoPuntaje.getText().trim());
            boolean modificado = registroPanel.getGestor().modificar(id, newPuntaje);
            if (modificado) {
                JOptionPane.showMessageDialog(this, "Puntaje del estudiante modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                buscarEstudiante();
                registroPanel.actualizarTexto();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el puntaje del estudiante. Verifique el ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el nuevo puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEstudiante() {
        String id = campoBuscarId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, busque un estudiante primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar a este estudiante?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = registroPanel.getGestor().eliminar(id);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                areaDatosEstudiante.setText("");
                campoBuscarId.setText("");
                campoNuevoPuntaje.setText("");
                registroPanel.actualizarTexto();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el estudiante. Verifique el ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}