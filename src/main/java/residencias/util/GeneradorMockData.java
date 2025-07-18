package residencias.util;

//Crear MockData para iniciar la aplicacion

import java.util.LinkedList;
import java.util.Random;

import residencias.estructuras.Estudiante;

public class GeneradorMockData {

    private static final String[] NOMBRES = {
        "Ana", "Luis", "Carlos", "Maria", "Juan", "Sofia", "Pedro", "Laura", "Andres", "Camila"
    };

    // Genera una lista de estudiantes aleatorios
    public static LinkedList<Estudiante> generarEstudiantes(int cantidad) {
        LinkedList<Estudiante> lista = new LinkedList<>();
        Random rand = new Random();

        for (int i = 0; i < cantidad; i++) {
            String id = "ID" + (1000 + i);
            String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
            int puntaje = rand.nextInt(101); // Puntaje entre 0 y 100
            lista.add(new Estudiante(id, nombre, puntaje));
        }
        return lista;
    }
}