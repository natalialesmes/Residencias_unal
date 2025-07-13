package residencias.estructuras;

public class Estudiante implements Comparable<Estudiante> {

    private String id;
    private String nombre;
    private int puntaje;
    private boolean residente;

    //Constructor
    public Estudiante (String id, String nombre, int puntaje){
        this.id = id;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.residente = false;
    }
    
    //Getters
    public String getId (){
        return id;
    }
    public String getNombre (){
        return nombre;
    }
    public int getPuntaje (){
        return puntaje;
    }
    public boolean getResidente (){
        return residente;
    }

    //Asignaciones
    public void setPuntaje (int newPuntaje){
        this.puntaje = newPuntaje;
    }
    public void darResidencia (){
        this.residente = true;
    }

    @Override
    public int compareTo (Estudiante otro){
        if (this.puntaje < otro.puntaje) return -1;
        if (this.puntaje > otro.puntaje) return 1;
        return 0;
    }

    @Override
    public String toString () {
        return "| Id: " + id + " | Nombre: " + nombre + " | Puntaje: " + puntaje + " | ¿Es residente? : " + (residente ? "Si" : "No");
    }
}
