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
    private GestorEstudiantes gestor;

    public GestionCupos(GestorEstudiantes gestor) {
        this.gestor = gestor;

        setTitle("Gestión de Cupos");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Cupos disponibles:"));
        campoCupos = new JTextField(10);
        add(campoCupos);

        btnAsignar = new JButton("Asignar cupos");
        add(btnAsignar);

        lblEstado = new JLabel("");
        add(lblEstado);

        btnAsignar.addActionListener(e -> {
            try {
                int cupos = Integer.parseInt(campoCupos.getText());
                if (cupos < 0) {
                    lblEstado.setText("Ingrese un número positivo.");
                    lblEstado.setForeground(Color.RED);
                } else {
                    gestor.asignarcupo(cupos);
                    lblEstado.setText("Cupos asignados: " + cupos);
                    lblEstado.setForeground(Color.GREEN);
                    campoCupos.setText(""); // Limpiar el campo de texto
                }
            } catch (NumberFormatException ex) {
                lblEstado.setText("Ingrese un número válido.");
            }
        });
    }
    
    
            public static void main(String[] args) {
                GestorEstudiantes gestor = new GestorEstudiantes();
                GestionCupos gestionCupos = new GestionCupos(gestor);
                gestionCupos.setVisible(true);
            }
        }