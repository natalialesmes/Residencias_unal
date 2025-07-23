package residencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionCupos extends JPanel {

    private JTextField campoCupos;
    private JButton btnAsignar;
    private JTextArea areaResultado;
    private RegistroEstudiantes registroPanel;

    public GestionCupos(RegistroEstudiantes registroPanel) {
        this.registroPanel = registroPanel;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Gestión de Cupos"));
        setBackground(Color.WHITE);

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoCupos = new JTextField();
        panelSuperior.add(new JLabel("Número de cupos:"));
        panelSuperior.add(campoCupos);

        btnAsignar = new JButton("Asignar Residencias");
        btnAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarCupos();
            }
        });

        panelSuperior.add(new JLabel(""));
        panelSuperior.add(btnAsignar);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setMargin(new Insets(10, 10, 10, 10));
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaResultado);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void asignarCupos() {
        try {
            int cupos = Integer.parseInt(campoCupos.getText().trim());
            registroPanel.getGestor().asignarcupo(cupos);
            int asignados = registroPanel.getGestor().asignarRes();

            areaResultado.setText("Residencias asignadas: " + asignados + " de " + cupos + " cupos disponibles.\n\n");
            areaResultado.append("Estudiantes asignados:\n");

            for (var est : registroPanel.getGestor().asignados()) {
                areaResultado.append("- " + est.getNombre() + " (ID: " + est.getId() + ")\n");
            }

            registroPanel.actualizarTexto();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para los cupos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
