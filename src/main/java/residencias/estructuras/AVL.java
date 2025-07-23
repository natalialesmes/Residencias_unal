package residencias.estructuras;
import java.util.*;

class NodoAVL {
    Estudiante estudiante;
    NodoAVL izquierda, derecha;
    int altura;

    NodoAVL(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.altura = 1;
    }
}

public class AVL {
    private NodoAVL raiz;
    private HashTable<String, Estudiante> mapaEstudiantes = new HashTable<>();

    public int altura(NodoAVL nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }

    private int balance(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private void actualizarAltura(NodoAVL nodo) {
        if (nodo != null) {
            nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
        }
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;

        // Realizar rotación
        x.derecha = y;
        y.izquierda = T2;

        // Actualizar alturas
        actualizarAltura(y);
        actualizarAltura(x);

        return x; // Nueva raíz de este subárbol
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;

        // Realizar rotación
        y.izquierda = x;
        x.derecha = T2;

        // Actualizar alturas
        actualizarAltura(x);
        actualizarAltura(y);

        return y; // Nueva raíz de este subárbol
    }

    public void insertar(Estudiante est) {
        raiz = insertar(raiz, est);
        mapaEstudiantes.add(est.getId(), est); // Asegúrate de que el ID es único en el mapa
    }

    private NodoAVL insertar(NodoAVL nodo, Estudiante est) {
        if (nodo == null) return new NodoAVL(est);


        int comparacionPuntaje = Integer.compare(est.getPuntaje(), nodo.estudiante.getPuntaje());

        if (comparacionPuntaje < 0) { // est.puntaje < nodo.estudiante.puntaje
            nodo.izquierda = insertar(nodo.izquierda, est);
        } else if (comparacionPuntaje > 0) { // est.puntaje > nodo.estudiante.puntaje
            nodo.derecha = insertar(nodo.derecha, est);
        } else {
            int comparacionID = est.getId().compareTo(nodo.estudiante.getId());
            if (comparacionID < 0) {
                nodo.izquierda = insertar(nodo.izquierda, est);
            } else if (comparacionID > 0) {
                nodo.derecha = insertar(nodo.derecha, est);
            } else {

                return nodo;
            }
        }

        actualizarAltura(nodo); // Actualizar altura después de la inserción

        int balance = balance(nodo);

        // Rotaciones basadas en el balance
        // Caso Izquierda-Izquierda
        if (balance > 1 && balance(nodo.izquierda) >= 0) // est.getPuntaje() < nodo.izquierda.estudiante.getPuntaje()
            return rotarDerecha(nodo);

        // Caso Derecha-Derecha
        if (balance < -1 && balance(nodo.derecha) <= 0) // est.getPuntaje() > nodo.derecha.estudiante.getPuntaje()
            return rotarIzquierda(nodo);

        // Caso Izquierda-Derecha
        if (balance > 1 && balance(nodo.izquierda) < 0) { // est.getPuntaje() > nodo.izquierda.estudiante.getPuntaje()
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && balance(nodo.derecha) > 0) { // est.getPuntaje() < nodo.derecha.estudiante.getPuntaje()
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public void eliminar(String id) {
        Estudiante est = mapaEstudiantes.find(id);
        if (est != null) {
            raiz = eliminar(raiz, est.getPuntaje(), est.getId());
            mapaEstudiantes.remove(id); 
        }
    }

    private NodoAVL eliminar(NodoAVL nodo, int puntaje, String id) {
        if (nodo == null) return nodo;

        int comparacionPuntaje = Integer.compare(puntaje, nodo.estudiante.getPuntaje());

        if (comparacionPuntaje < 0) {
            nodo.izquierda = eliminar(nodo.izquierda, puntaje, id);
        } else if (comparacionPuntaje > 0) {
            nodo.derecha = eliminar(nodo.derecha, puntaje, id);
        } else { 
            if (nodo.estudiante.getId().equals(id)) { 

                if (nodo.izquierda == null || nodo.derecha == null) {
                    return (nodo.izquierda != null) ? nodo.izquierda : nodo.derecha; 
                } else { 
                    NodoAVL sucesor = minValorNodo(nodo.derecha);
                    nodo.estudiante = sucesor.estudiante; 
                    nodo.derecha = eliminar(nodo.derecha, sucesor.estudiante.getPuntaje(), sucesor.estudiante.getId());
                }
            } else {
                nodo.derecha = eliminar(nodo.derecha, puntaje, id);
            }
        }

        if (nodo == null) return nodo; 

        actualizarAltura(nodo); 

        int balance = balance(nodo);


        if (balance > 1 && balance(nodo.izquierda) >= 0)
            return rotarDerecha(nodo);
        if (balance > 1 && balance(nodo.izquierda) < 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Derecha o Derecha-Izquierda
        if (balance < -1 && balance(nodo.derecha) <= 0)
            return rotarIzquierda(nodo);
        if (balance < -1 && balance(nodo.derecha) > 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL minValorNodo(NodoAVL nodo) {
        if (nodo == null) return null; 
        NodoAVL actual = nodo;
        while (actual.izquierda != null)
            actual = actual.izquierda;
        return actual;
    }

    public Estudiante buscarPorID(String id) {
        return mapaEstudiantes.find(id);
    }

    public void modificarPuntaje(String id, int nuevoPuntaje) {
        Estudiante est = mapaEstudiantes.find(id);
        if (est != null) {
            eliminar(id);
            est.setPuntaje(nuevoPuntaje);
            insertar(est);
        }
    }

    public void listarCreciente() {
        listarCreciente(raiz);
    }

    private void listarCreciente(NodoAVL nodo) {
        if (nodo != null) {
            listarCreciente(nodo.izquierda);
            System.out.println(nodo.estudiante);
            listarCreciente(nodo.derecha);
        }
    }

    public void asignarCupos(int cupos) {
        List<Estudiante> ordenados = new ArrayList<>();
        recolectarEnOrden(raiz, ordenados);
        System.out.println("Estudiantes con residencia:");
        for (int i = ordenados.size() - 1; i >= Math.max(0, ordenados.size() - cupos); i--) {
            ordenados.get(i).darResidencia();
            System.out.println(ordenados.get(i));
        }
        System.out.println("\nEstudiantes sin residencia:");
        for (int i = 0; i < Math.max(0, ordenados.size() - cupos); i++) {
            System.out.println(ordenados.get(i));
        }
    }

    private void recolectarEnOrden(NodoAVL nodo, List<Estudiante> lista) {
        if (nodo != null) {
            recolectarEnOrden(nodo.izquierda, lista);
            lista.add(nodo.estudiante);
            recolectarEnOrden(nodo.derecha, lista);
        }
    }
}
