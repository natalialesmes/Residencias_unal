package residencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import residencias.logica.GestorResidencias;

public class RegistroEstudiantes extends JPanel {

    private JTextField campoNombre;
    private JTextField campoCodigo;
    private JComboBox<String> comboPrioridad;
    private GestorResidencias gestor;

    public RegistroEstudiantes() {
        gestor = new GestorResidencias(); // clase lógica que controla la estructura (por ejemplo, MinHeap)

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Registro de Estudiantes"));

        JPanel formulario = new JPanel(new GridLayout(6, 1, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoNombre = new JTextField();
        campoCodigo = new JTextField();
        comboPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});

        formulario.add(new JLabel("Nombre del estudiante:"));
        formulario.add(campoNombre);

        formulario.add(new JLabel("Código del estudiante:"));
        formulario.add(campoCodigo);

        formulario.add(new JLabel("Prioridad de ingreso:"));
        formulario.add(comboPrioridad);

        add(formulario, BorderLayout.CENTER);

        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.setBackground(new Color(34, 139, 34));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEstudiante();
            }
        });

        add(botonRegistrar, BorderLayout.SOUTH);
    }

    private void registrarEstudiante() {
        String nombre = campoNombre.getText().trim();
        String codigo = campoCodigo.getText().trim();
        String prioridadStr = (String) comboPrioridad.getSelectedItem();
        int prioridad;

        switch (prioridadStr) {
            case "Alta": prioridad = 1; break;
            case "Media": prioridad = 2; break;
            case "Baja": prioridad = 3; break;
            default: prioridad = 4;
        }

        if (nombre.isEmpty() || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = gestor.agregarEstudiante(nombre, codigo, prioridad);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.");
            campoNombre.setText("");
            campoCodigo.setText("");
            comboPrioridad.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Ya existe un estudiante con ese código.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
