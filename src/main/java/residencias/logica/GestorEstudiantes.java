package residencias.logica;
//Importamos estructuras a utilizar
import java.util.LinkedList;

import residencias.estructuras.Estudiante;
import residencias.estructuras.HashTable;
import residencias.estructuras.MinHeap;

//Logica de la aplicacion
public class GestorEstudiantes {
    //Acceder por Id
    private HashTable <String, Estudiante> tabla;
    //Priorizar estudiantes
    private MinHeap <Estudiante> heap;
    //Cupos disponibles
    private int cupos;

    //Constructor
    public GestorEstudiantes (){
        tabla = new HashTable <>();
        heap = new MinHeap <>();
        cupos = 0;
    }

    // CUPOS disponibles
    public void asignarcupo (int cupos){
        this.cupos = cupos;
    } 
    // REGISTRO de estudiante
    public boolean registro (String id, String nombre, int puntaje){
        if (tabla.hasKey(id)) return false; // Si ya esta no hace nada
        Estudiante n = new Estudiante(id, nombre, puntaje);
        tabla.add(id, n); //Guarda en la tabla con su ID
        heap.insert(n); // Guarda en el Heap
        return true;
    }

    //CONSULTAR estudiante
    public Estudiante consultar (String id){
        return tabla.find(id); // Busca el Id y sus datos en la tabla
    }

    //ELIMINAR estudiante
    public boolean eliminar (String id){
        Estudiante n = tabla.find(id);
        if (n == null) return false; //No hay ningun estudiante "n"

        tabla.remove(id); //Elimina Id de la tabla
        heap.remove(n); //Elimina estudiante del heap
        return true;
    }

    //MODIFICA puntaje
    public boolean modificar (String id, int newpuntaje){
        Estudiante n = tabla.find(id);
        if (n == null) return false; //No hay estudiante "n" para modificar
        
        heap.remove(n); //Quita el estudiante del heap
        n.setPuntaje(newpuntaje); //Actualiza el puntaje del estudiante
        heap.insert(n); //Agrega al estudiante con su puntaje actualizado
        return true;
    }

    //ASIGNAR residencias a estudiantes con menores puntajes
    public int asignarRes (){
        LinkedList <Estudiante> ests = tabla.values();
        ests.sort(Estudiante::compareTo); //Pasa menor puntaje primero

        int asigna = 0;
        for (int i = 0; i < ests.size(); i++){
            Estudiante n = ests.get(i);
            if (asigna >= cupos) break; //Ya no hay cupos para asignar
            if(!n.getResidente()){ //El estudiante aun no es residente
                n.darResidencia();
                asigna ++;
            }
        }
        return asigna;
    }

    //Puntajes por orden
    public LinkedList <Estudiante> ordenados (){
        LinkedList <Estudiante> lista = tabla.values();
        lista.sort(Estudiante::compareTo);
        return lista;
    }

    //Obtener estudiantes asignados
    public LinkedList <Estudiante> asignados (){
        LinkedList <Estudiante> asignados = new LinkedList <> (); //Nueva lista con los asignados
        LinkedList <Estudiante> lista = tabla.values(); //Guarda valores de la tabla
        for (int i = 0; i < lista.size(); i++){
            Estudiante n = lista.get(i);
            if(n.getResidente()){
                asignados.add(n);
            }
        }
        return asignados;
    }

    //Obtener estudiantes no asignados
    public LinkedList <Estudiante> noAsignados(){
        LinkedList <Estudiante> noasig = new LinkedList <>(); //Nueva lista con los no asignados
        LinkedList <Estudiante> lista = tabla.values(); //Guarda valores de la tabla
        for (int i = 0; i < lista.size(); i ++){
            Estudiante n = lista.get(i);
            if(!n.getResidente()){ //El estudiante no es residente;
                noasig.add(n);
            }
        }
        return noasig;
    }
}
