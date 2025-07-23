package residencias.estructuras;

import java.util.LinkedList;

public class HashTable <K, V> {
    private static class Entrada <K, V>{
        K key;
        V valor;
        Entrada <K, V> next;
        

        Entrada (K k, V v){
            this.key = k;
            this.valor = v;
        }
    }
    private static final int DEFAULT_CAP = 1000;
    private Entrada <K, V>[] tabla;
    private int cap = 1000;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.cap = DEFAULT_CAP;
        this.tabla = (Entrada <K, V>[]) new Entrada[cap];
        this.size = 0;
    }

    private int hash (K key){
        return (key.hashCode () & 0x7fffffff) % cap;
    }

    public boolean add (K key, V valor){
        // Verificar si la tabla necesita ser redimensionada antes de añadir
        if ((double) size / cap >= LOAD_FACTOR_THRESHOLD) {
            resize(); // Llamar al método de redimensionamiento
        }
        int idx = hash(key);
        Entrada <K, V> curr = tabla[idx];

        while (curr != null){
            if (curr.key.equals(key)){
                curr.valor = valor; // Actualizar valor si la clave ya existe
                return true;
            }
            curr = curr.next;
        }
        Entrada <K, V> nuevo = new Entrada <> (key, valor);
        nuevo.next = tabla [idx];
        tabla [idx] = nuevo;
        size ++;
        return true;
    }

    public V find (K key){
        int idx = hash(key);
        Entrada <K, V> curr = tabla [idx];

        while (curr != null){
            if (curr.key.equals(key)){
                return curr.valor; //Encontrado
            }
            curr = curr.next;
        } 
        return null; //No encontrado
    }

    public int size () {
        return size;
    }

    public boolean remove (K key){
        int idx = hash(key);
        Entrada <K, V> curr = tabla[idx];
        Entrada <K, V> prev = null;

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    tabla[idx] = curr.next;
                } 
                else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false; 
    }
    public boolean hasKey (K key){
        return find (key) != null;
    }

    //Para devolver todos los elementos
    public LinkedList<V> values() {
        LinkedList<V> lista = new LinkedList<>();
        for (int i = 0; i < cap; i++) {
            Entrada<K, V> curr = tabla[i];
            while (curr != null) {
                lista.add(curr.valor);
                curr = curr.next;
            }
        }
        return lista;
    }

    public void print() {
        for (int i = 0; i < cap; i++) {
            Entrada<K, V> curr = tabla[i];
            while (curr != null) {
                System.out.println("[" + i + "] " + curr.key + " => " + curr.valor);
                curr = curr.next;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCap = cap;
        cap *= 2; // Duplicar la capacidad
        Entrada<K, V>[] oldTabla = tabla; // Guardar la referencia a la tabla antigua
        tabla = (Entrada<K, V>[]) new Entrada[cap]; // Crear una nueva tabla con el doble de capacidad
        int oldSize = size; // Guardar el tamaño actual antes de resetearlo
        size = 0; // Resetear el tamaño temporalmente, se reconstruirá

        // Reinsertar todos los elementos de la tabla antigua a la nueva
        for (int i = 0; i < oldCap; i++) {
            Entrada<K, V> entry = oldTabla[i];
            while (entry != null) {
                // Importante: No uses el método add directamente aquí
                // porque add llama a resize() recursivamente.
                // En su lugar, inserta directamente en la nueva tabla.
                int newIdx = hash(entry.key);
                Entrada<K, V> newEntry = new Entrada<>(entry.key, entry.valor);
                newEntry.next = tabla[newIdx];
                tabla[newIdx] = newEntry;
                size++; // Incrementar el tamaño para la nueva tabla
                entry = entry.next;
            }
        }
    }
}