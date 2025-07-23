package residencias.datos;

//Almacenar los datos en archivos (Csv)

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import residencias.estructuras.Estudiante;

public class AlmacenamientoArchivos {
    private static final String ARCHIVO_CSV = "estudiantes.csv";
    private static final Path RUTA_CSV = Paths.get(ARCHIVO_CSV);

    // 🗂️ Guardar estudiantes en CSV con UTF-8
    public boolean guardar(List<Estudiante> estudiantes) {
        try (BufferedWriter bw = Files.newBufferedWriter(RUTA_CSV, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(bw)) {
            for (Estudiante e : estudiantes) {
                writer.printf("%s,%s,%d,%s%n",
                        e.getId(),
                        e.getNombre(),
                        e.getPuntaje(),
                        e.getResidente() ? "true" : "false");
            }
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar en CSV: " + e.getMessage());
            return false;
        }
    }

    // 📤 Cargar estudiantes desde CSV con validación
    public List<Estudiante> cargar() {
        List<Estudiante> lista = new ArrayList<>();
        if (!Files.exists(RUTA_CSV)) return lista;

        try (BufferedReader reader = Files.newBufferedReader(RUTA_CSV, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 4) {
                    System.err.println("⚠️ Línea malformada ignorada: " + linea);
                    continue;
                }

                try {
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    int puntaje = Integer.parseInt(partes[2].trim());
                    boolean residente = Boolean.parseBoolean(partes[3].trim());

                    Estudiante e = new Estudiante(id, nombre, puntaje);
                    if (residente) e.darResidencia();
                    lista.add(e);
                } catch (NumberFormatException ex) {
                    System.err.println("⚠️ Error al convertir datos numéricos en línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error al leer desde CSV: " + e.getMessage());
        }

        return lista;
    }
}
