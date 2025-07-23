package residencias.datos;

import java.util.List;

import residencias.estructuras.Estudiante;

//Interfaz para almacenar datos
public interface IAlmacenamiento {

    void setCupos(int cupos);
    int getCupos();

    void agregarEstudiante(Estudiante estudiante);
    List<Estudiante> obtenerEstudiantes();
    void limpiarEstudiantes();
}
