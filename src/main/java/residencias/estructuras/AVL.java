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

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;
        x.derecha = y;
        y.izquierda = T2;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;
        y.izquierda = x;
        x.derecha = T2;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        return y;
    }

    public void insertar(Estudiante est) {
        raiz = insertar(raiz, est);
        mapaEstudiantes.add(est.getId(), est);
    }

    private NodoAVL insertar(NodoAVL nodo, Estudiante est) {
        if (nodo == null) return new NodoAVL(est);

        if (est.getPuntaje() < nodo.estudiante.getPuntaje())
            nodo.izquierda = insertar(nodo.izquierda, est);
        else
            nodo.derecha = insertar(nodo.derecha, est);

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
        int balance = balance(nodo);

        if (balance > 1 && est.getPuntaje() < nodo.izquierda.estudiante.getPuntaje())
            return rotarDerecha(nodo);
        if (balance < -1 && est.getPuntaje() > nodo.derecha.estudiante.getPuntaje())
            return rotarIzquierda(nodo);
        if (balance > 1 && est.getPuntaje() > nodo.izquierda.estudiante.getPuntaje()) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && est.getPuntaje() < nodo.derecha.estudiante.getPuntaje()) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    public void eliminar(String id) {
        Estudiante est = mapaEstudiantes.find(id);
        if (est != null) {
            raiz = eliminar(raiz, est.getPuntaje());
            mapaEstudiantes.remove(id);
        }
    }

    private NodoAVL eliminar(NodoAVL nodo, int puntaje) {
        if (nodo == null) return nodo;

        if (puntaje < nodo.estudiante.getPuntaje())
            nodo.izquierda = eliminar(nodo.izquierda, puntaje);
        else if (puntaje > nodo.estudiante.getPuntaje())
            nodo.derecha = eliminar(nodo.derecha, puntaje);
        else {
            if (nodo.izquierda == null || nodo.derecha == null) {
                NodoAVL temp;
                if (nodo.izquierda != null) {
                    temp = nodo.izquierda;
                } else {
                    temp = nodo.derecha;
                }
                nodo = temp;
            } else {
                NodoAVL sucesor = minValorNodo(nodo.derecha);
                nodo.estudiante = sucesor.estudiante;
                nodo.derecha = eliminar(nodo.derecha, sucesor.estudiante.getPuntaje());
            }
        }

        if (nodo == null) return nodo;

        nodo.altura = Math.max(altura(nodo.izquierda), altura(nodo.derecha)) + 1;
        int balance = balance(nodo);

        if (balance > 1 && balance(nodo.izquierda) >= 0)
            return rotarDerecha(nodo);
        if (balance > 1 && balance(nodo.izquierda) < 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && balance(nodo.derecha) <= 0)
            return rotarIzquierda(nodo);
        if (balance < -1 && balance(nodo.derecha) > 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL minValorNodo(NodoAVL nodo) {
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
        for (int i = 0; i < Math.min(cupos, ordenados.size()); i++) {
            ordenados.get(i).darResidencia();
            System.out.println(ordenados.get(i));
        }
        System.out.println("Estudiantes sin residencia:");
        for (int i = cupos; i < ordenados.size(); i++) {
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
