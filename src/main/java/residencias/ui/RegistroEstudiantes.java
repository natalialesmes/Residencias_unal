package residencias.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent; // Importar GestorEstudiantes
import java.awt.event.ActionListener; // Importar Estudiante principal
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import residencias.estructuras.Estudiante;
import residencias.logica.GestorEstudiantes;

public class RegistroEstudiantes extends JPanel {

    private JTextField campoNombre;
    private JTextField campoCodigo;
    private JTextField campoPromedio;
    private JTextArea areaResultado;
    private GestorEstudiantes gestor;

    // Lógica simple incorporada: usamos un MinHeap basado en promedio
    private PriorityQueue<Estudiante> minHeap;

    public RegistroEstudiantes(GestorEstudiantes gestor) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        this.gestor = gestor;

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
        int puntaje;

        try {
            puntaje = Integer.parseInt(campoPromedio.getText());

            if (nombre.isEmpty() || codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Usar el método registro del gestor
            if (gestor.registro(codigo, nombre, puntaje)) { // Pasar ID como primer argumento
                JOptionPane.showMessageDialog(this, "Estudiante registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarAreaResultado(); // Actualizar la visualización
                campoNombre.setText("");
                campoCodigo.setText("");
                campoPromedio.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un estudiante con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Puntaje inválido. Debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarAreaResultado() {
        StringBuilder sb = new StringBuilder();
        // Obtener la lista de estudiantes del gestor, ya ordenados por puntaje
        LinkedList<Estudiante> estudiantesOrdenados = gestor.ordenados();

        sb.append("Estudiantes registrados (ordenados por menor puntaje):\n\n");
        for (Estudiante e : estudiantesOrdenados) {
            // Asegurarse de que los nombres de los atributos sean correctos (getId, getNombre, getPuntaje)
            sb.append(String.format("Nombre: %-15s Código: %-10s Puntaje: %d\n", e.getNombre(), e.getId(), e.getPuntaje()));
        }

        areaResultado.setText(sb.toString());
    }
}
