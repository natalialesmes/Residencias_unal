package residencias.ui;

import residencias.logica.GestorEstudiantes;
import residencias.estructuras.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroEstudiantes extends JPanel {

    private JTextField campoNombre;
    private JTextField campoId;
    private JTextField campoPuntaje;
    private JTextArea areaResultado;
    private GestorEstudiantes gestor;

    public RegistroEstudiantes() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        gestor = new GestorEstudiantes();

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        campoNombre = new JTextField();
        campoId = new JTextField();
        campoPuntaje = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(campoNombre);
        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(campoId);
        panelFormulario.add(new JLabel("Puntaje:"));
        panelFormulario.add(campoPuntaje);

        JButton btnAgregar = new JButton("Registrar Estudiante");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });
        panelFormulario.add(btnAgregar);

        this.add(panelFormulario, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultado.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(areaResultado);

        this.add(scroll, BorderLayout.CENTER);
    }

    private void agregarEstudiante() {
        String nombre = campoNombre.getText().trim();
        String id = campoId.getText().trim();
        String puntajeStr = campoPuntaje.getText().trim();

        if (nombre.isEmpty() || id.isEmpty() || puntajeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int puntaje = Integer.parseInt(puntajeStr);

            boolean registrado = gestor.registro(id, nombre, puntaje);
            if (registrado) {
                campoNombre.setText("");
                campoId.setText("");
                campoPuntaje.setText("");
                actualizarTexto();
            } else {
                JOptionPane.showMessageDialog(this, "El estudiante ya está registrado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El puntaje debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-20s %-10s %-15s\n", "ID", "Nombre", "Puntaje", "Residente"));
        sb.append("--------------------------------------------------------------\n");

        for (Estudiante est : gestor.ordenados()) {
            sb.append(String.format("%-10s %-20s %-10d %-15s\n", est.getId(), est.getNombre(), est.getPuntaje(), est.getResidente() ? "Sí" : "No"));
        }

        areaResultado.setText(sb.toString());
    }

    public GestorEstudiantes getGestor() {
        return gestor;
    }
}

