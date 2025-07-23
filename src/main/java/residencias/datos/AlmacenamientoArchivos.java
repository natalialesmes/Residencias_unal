package residencias.datos;
//Almacenar los datos en archivos (Json/Csv)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import residencias.estructuras.Estudiante;

public class AlmacenamientoArchivos {

    private static final String ARCHIVO_CSV = "estudiantes.csv";


    // 🗂️ Guardar estudiantes en CSV
    public boolean guardarEnCsv(List<Estudiante> estudiantes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_CSV))) {
            for (Estudiante e : estudiantes) {
                writer.printf("%s,%s,%d,%s%n",
                        e.getId(),
                        e.getNombre(),
                        e.getPuntaje(),
                        e.getResidente() ? "true" : "false");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar en CSV: " + e.getMessage());
            return false;
        }
    }

    // 📤 Cargar estudiantes desde CSV
    public List<Estudiante> cargarDesdeCsv() {
        List<Estudiante> lista = new ArrayList<>();
        if (!Files.exists(Paths.get(ARCHIVO_CSV))) return lista;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    Estudiante e = new Estudiante(
                            partes[0].trim(),
                            partes[1].trim(),
                            Integer.parseInt(partes[2].trim())
                    );
                    if (Boolean.parseBoolean(partes[3].trim())) e.darResidencia();
                    lista.add(e);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar desde CSV: " + e.getMessage());
        }
        return lista;
    }
}
