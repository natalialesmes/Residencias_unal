package residencias.util;

import java.util.LinkedList;
import java.util.Random;

import residencias.estructuras.Estudiante;

//Crear MockData para iniciar la aplicacion

public class GeneradorMockData {

    private static final String[] NOMBRES = {
        "Ana", "Luis", "Carlos", "Maria", "Juan", "Sofia", "Pedro", "Laura", "Andres", "Camila"
    };

    private static final String[] APELLIDOS = {
    "García", "Rodríguez", "Martínez", "Pérez", "López", "Gómez", "Fernández"
    };

        private static final Random rand = new Random();

    // 🎲 Genera un ID único a partir de un número
    private static String generarIdUnico(int i) {
        return "ID" + (1000 + i);
    }

    // 👥 Genera un nombre aleatorio
    private static String generarNombreAleatorio() {
        String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
        String apellido = APELLIDOS[rand.nextInt(APELLIDOS.length)];
        return nombre + " " + apellido;
    }

    // 🧪 Genera una lista de estudiantes aleatorios
    public static LinkedList<Estudiante> generarEstudiantes(int cantidad) {
        LinkedList<Estudiante> lista = new LinkedList<>();

        for (int i = 0; i < cantidad; i++) {
            String id = generarIdUnico(i);
            String nombreCompleto = generarNombreAleatorio();
            int puntaje = rand.nextInt(101); // Puntaje entre 0 y 100
            lista.add(new Estudiante(id, nombreCompleto, puntaje));
        }

        return lista;
    }
}