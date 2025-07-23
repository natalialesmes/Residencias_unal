package residencias.datos;

import java.util.ArrayList;
import java.util.List;

import residencias.estructuras.Estudiante;

//Almacenar los datos en RAM
public class AlmacenamientoMemoria {
    private final List<Estudiante> estudiantes = new ArrayList<>();

    public boolean agregarEstudiante(Estudiante e) {
        if (e == null || buscarPorId(e.getId()) != null) return false;
        estudiantes.add(e);
        return true;
    }

    public boolean eliminarEstudiantePorId(String id) {
        return estudiantes.removeIf(e -> e.getId().equals(id));
    }

    public List<Estudiante> obtenerTodos() {
        return new ArrayList<>(estudiantes);
    }

    public Estudiante buscarPorId(String id) {
        for (Estudiante e : estudiantes) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    public int cantidad() {
        return estudiantes.size();
    }
}