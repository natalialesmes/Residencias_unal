package residencias.datos;

import java.util.ArrayList;
import java.util.List;

import residencias.estructuras.Estudiante;

//Almacenar los datos en RAM
public class AlmacenamientoMemoria implements IAlmacenamiento {
    
    private int cupos;
    private final List<Estudiante> estudiantes = new ArrayList<>();

    @Override
    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    @Override
    public int getCupos() {
        return cupos;
    }

    @Override
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    @Override
    public List<Estudiante> obtenerEstudiantes() {
        return new ArrayList<>(estudiantes); // Devolver copia para no modificar directamente
    }

    @Override
    public void limpiarEstudiantes() {
        estudiantes.clear();
    }
}