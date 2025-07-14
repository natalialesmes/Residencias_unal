package residencias.estructuras;

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
        int idx = hash(key);
        Entrada <K, V> curr = tabla[idx];

        while (curr != null){
            if (curr.key.equals(key)){
                return false;
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

    public void print() {
        for (int i = 0; i < cap; i++) {
            Entrada<K, V> curr = tabla[i];
            while (curr != null) {
                System.out.println("[" + i + "] " + curr.key + " => " + curr.valor);
                curr = curr.next;
            }
        }
    }
}