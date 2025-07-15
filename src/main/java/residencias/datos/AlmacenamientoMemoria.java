package residencias.datos;
//Almacenar los datos en RAM
public class AlmacenamientoMemoria {
    private List<Estudiante> estudiantes;

    public AlmacenamientoMemoria() {
        estudiantes = new ArrayList<>();
    }

    public boolean agregarEstudiante(Estudiante e) {
        if (buscarPorId(e.getId()) == null) {
            estudiantes.add(e);
            return true;
        }
        return false; // Ya existe un estudiante con ese ID
    }

    public boolean eliminarEstudiantePorId(String id) {
        return estudiantes.removeIf(e -> e.getId().equals(id));
    }

    public List<Estudiante> obtenerTodos() {
        return new ArrayList<>(estudiantes);
    }

    public Estudiante buscarPorId(String id) {
        for (Estudiante e : estudiantes) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public int cantidad() {
        return estudiantes.size();
    }
}
