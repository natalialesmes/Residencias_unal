package residencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;

public class RegistroEstudiantes extends JPanel {

    private JTextField campoNombre;
    private JTextField campoCodigo;
    private JTextField campoPromedio;
    private JTextArea areaResultado;

    // Lógica simple incorporada: usamos un MinHeap basado en promedio
    private PriorityQueue<Estudiante> minHeap;

    public RegistroEstudiantes() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Inicializar MinHeap (por promedio)
        minHeap = new PriorityQueue<>();

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelFormulario.setBackground(new Color(240, 248, 255));

        campoNombre = new JTextField();
        campoCodigo = new JTextField();
        campoPromedio = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(campoNombre);

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(campoCodigo);

        panelFormulario.add(new JLabel("Promedio:"));
        panelFormulario.add(campoPromedio);

        JButton btnAgregar = new JButton("Agregar Estudiante");
        btnAgregar.setBackground(new Color(100, 149, 237));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });

        panelFormulario.add(btnAgregar);

        add(panelFormulario, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultado.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(areaResultado);

        add(scroll, BorderLayout.CENTER);
    }

    private void agregarEstudiante() {
        String nombre = campoNombre.getText();
        String codigo = campoCodigo.getText();
        double promedio;

        try {
            promedio = Double.parseDouble(campoPromedio.getText());

            if (nombre.isEmpty() || codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Estudiante estudiante = new Estudiante(nombre, codigo, promedio);
            minHeap.add(estudiante);
            actualizarAreaResultado();

            campoNombre.setText("");
            campoCodigo.setText("");
            campoPromedio.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Promedio inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarAreaResultado() {
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Estudiante> copia = new PriorityQueue<>(minHeap);

        sb.append("Estudiantes registrados (ordenados por menor promedio):\n\n");
        while (!copia.isEmpty()) {
            Estudiante e = copia.poll();
            sb.append(String.format("Nombre: %-15s Código: %-10s Promedio: %.2f\n", e.nombre, e.codigo, e.promedio));
        }

        areaResultado.setText(sb.toString());
    }

    // Clase interna para representar estudiantes
    private static class Estudiante implements Comparable<Estudiante> {
        String nombre;
        String codigo;
        double promedio;

        public Estudiante(String nombre, String codigo, double promedio) {
            this.nombre = nombre;
            this.codigo = codigo;
            this.promedio = promedio;
        }

        @Override
        public int compareTo(Estudiante otro) {
            return Double.compare(this.promedio, otro.promedio); // Orden ascendente por promedio
        }
    }
}
