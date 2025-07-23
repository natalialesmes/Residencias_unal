package residencias.ui;

//Panel para administrar los cupos de las residencias

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import residencias.logica.GestorEstudiantes;

public class GestionCupos extends JFrame {

    private JTextField campoCupos;
    private JButton btnAsignar;
    private JLabel lblEstado;
    private final GestorEstudiantes gestor;

    public GestionCupos(GestorEstudiantes gestor) {
        this.gestor = gestor;

        setTitle("Gestión de Cupos");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // Etiqueta + campo de texto
        add(new JLabel("Cupos disponibles:"));
        campoCupos = new JTextField(10);
        add(campoCupos);

        // Botón de asignar
        btnAsignar = new JButton("Asignar cupos");
        add(btnAsignar);

        // Etiqueta de estado
        lblEstado = new JLabel();
        add(lblEstado);

        // Acción del botón
        btnAsignar.addActionListener(e -> {
            String texto = campoCupos.getText().trim();
            try {
                int cupos = Integer.parseInt(texto);
                if (cupos <= 0) {
                    mostrarMensaje("Ingrese un número mayor que cero.", Color.RED);
                } else {
                    gestor.asignarcupo(cupos);
                    mostrarMensaje("Cupos asignados: " + cupos, Color.GREEN);
                    campoCupos.setText(""); // Limpia el campo
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje("Ingrese un número válido.", Color.RED);
            }
        });
    }

    // Método auxiliar para mostrar mensajes de estado
    private void mostrarMensaje(String mensaje, Color color) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(color);
    }

    // Para pruebas individuales
    public static void main(String[] args) {
        GestorEstudiantes gestor = new GestorEstudiantes();
        GestionCupos gestionCupos = new GestionCupos(gestor);
        gestionCupos.setVisible(true);
    }
}