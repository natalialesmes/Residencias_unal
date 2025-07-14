package residencias.estructuras;

import java.util.NoSuchElementException;

public class MinHeap<T extends Comparable<T>> { // <T extends Comparable<T>> asegura que T sea comparable
    private T[] elements; // Usamos Object[] para almacenar cualquier tipo T
    private int size;         // Número actual de elementos en el heap

    /**
     * Constructor para inicializar un MinHeap genérico con la capacidad por defecto (100).
     */
    @SuppressWarnings("unchecked") //Para que no lance advertencia
    public MinHeap() {
        elements = (T[]) new Comparable[100];
        size = 0;
    }

    /**
     * Constructor para inicializar un MinHeap genérico con una capacidad inicial específica.
     * @param initialCapacity La capacidad inicial del arreglo.
     * @throws IllegalArgumentException si la capacidad inicial es menor o igual a cero.
     */
    @SuppressWarnings("unchecked") //Para que no lance advertencia
    public MinHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("La capacidad inicial debe ser un número positivo.");
        }
        this.elements = (T[])new Comparable[initialCapacity];
        this.size = 0;
    }

    /**
     * Retorna el número actual de elementos en el heap.
     * @return El tamaño actual del heap.
     */
    public int size() {
        return size;
    }

    /**
     * Verifica si el heap está vacío.
     * @return true si el heap no contiene elementos, false en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna la capacidad actual del arreglo subyacente.
     * Útil para observar el redimensionamiento.
     */
    public int getCapacity() {
        return elements.length;
    }

    /**
     * Obtiene el índice del padre de un nodo.
     * @param childIndex El índice del nodo hijo.
     * @return El índice del padre.
     */
    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    /**
     * Obtiene el índice del hijo izquierdo de un nodo.
     * @param parentIndex El índice del nodo padre.
     * @return El índice del hijo izquierdo.
     */
    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    /**
     * Obtiene el índice del hijo derecho de un nodo.
     * @param parentIndex El índice del nodo padre.
     * @return El índice del hijo derecho.
     */
    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    /**
     * Intercambia dos elementos en el arreglo.
     * @param index1 El índice del primer elemento.
     * @param index2 El índice del segundo elemento.
     */
    private void swap(int index1, int index2) {
        T temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    /**
     * **Redimensiona el arreglo si se llega a la maxima capacidad del arreglo
     * Crea un nuevo arreglo con una capacidad aumentada (generalmente el doble)
     * y copia todos los elementos existentes al nuevo arreglo.
     */
    @SuppressWarnings("unchecked") //Para que no lance advertencia
    private void ensureCapacity() {
        if (size == elements.length) { // Si el arreglo está lleno
            int newCapacity = elements.length * 2; // Duplicamos la capacidad
            // Creamos un nuevo arreglo temporal con la nueva capacidad
            T[] newElements = (T[])new Comparable[newCapacity];
            // Copiamos manualmente los elementos usando un bucle for
            for(int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements; // Asignamos el nuevo arreglo como el arreglo del heap
            //System.out.println("DEBUG: Redimensionando de " + (newCapacity / 2) + " a " + newCapacity + " elementos.");
        }
    }

    /**
     * Agrega un elemento al heap y mantiene la propiedad del min-heap.
     * @param element El elemento a agregar.
     */
    public void insert(T element) {
        ensureCapacity(); // Aseguramos que haya espacio
        elements[size] = element; // Agrega el elemento al final del heap
        siftUp(size); // Restaura la propiedad del heap desde el nuevo elemento
        size++;       // Incrementa el tamaño
    }
    /**
     * Elimina un elemento al heap y mantiene la propiedad del min-heap.
     * @param element El elemento a agregar.
     */
    public boolean remove (T element) {
        for (int i = 0; i < size; i++){
            if(elements[i].equals(element)){
                elements[i] = elements[i - 1]; //Utiliza el ultimo
                size--; //Disminuye tamaño
                siftDown(i);
                siftUp(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina y retorna el elemento mínimo (la raíz) del heap.
     * @return El elemento mínimo del heap.
     * @throws NoSuchElementException Si el heap está vacío.
     */
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        T min = elements[0]; // El elemento mínimo es siempre la raíz
        elements[0] = elements[size - 1]; // Mueve el último elemento a la raíz
        size--; // Disminuye el tamaño
        siftDown(0); // Restaura la propiedad del heap desde la raíz
        return min;
    }

    /**
     * Retorna el elemento mínimo (la raíz) del heap sin eliminarlo.
     * @return El elemento mínimo del heap.
     * @throws NoSuchElementException Si el heap está vacío.
     */
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return elements[0];
    }

    /**
     * Mueve un elemento hacia arriba en el heap para restaurar la propiedad del min-heap.
     * @param currentIndex El índice del elemento que se va a "sift up".
     */
    private void siftUp(int currentIndex) {
        T currentElement = elements[currentIndex];

        // Mientras el nodo actual no sea la raíz y sea menor que su padre
        while (currentIndex > 0) {
            int parentIndex = getParentIndex(currentIndex);
            T parentElement = elements[parentIndex];
            // Usamos compareTo() para comparar elementos genéricos
            if (currentElement.compareTo(parentElement) < 0) {
                swap(currentIndex, parentIndex); // Intercambia con el padre
                currentIndex = parentIndex; // Mueve al índice del padre
            } else {
                break; // El elemento ya está en su lugar correcto
            }
        }
    }

    /**
     * Mueve un elemento hacia abajo en el heap para restaurar la propiedad del min-heap.
     * @param currentIndex El índice del elemento al que se le va a aplicar SiftDown
     */
    private void siftDown(int currentIndex) {
        int minIndex = currentIndex; // Asume que el actual es el mínimo inicialmente
        int leftChildIndex = getLeftChildIndex(currentIndex);
        int rightChildIndex = getRightChildIndex(currentIndex);

        T currentElement = elements[currentIndex];

        // Compara con el hijo izquierdo
        if (leftChildIndex < size) {
            T leftChild = elements[leftChildIndex];
            T minElement = elements[minIndex];

            if (leftChild.compareTo(minElement) < 0) {
                minIndex = leftChildIndex;
            }
        }

        // Compara con el hijo derecho
        if (rightChildIndex < size) {
            T rightChild = elements[rightChildIndex];
            T minElement = elements[minIndex]; // Obtiene el elemento en el actual minIndex

            if (rightChild.compareTo(minElement) < 0) {
                minIndex = rightChildIndex;
            }
        }

        // Si el elemento mínimo no es el actual (significa que hubo un hijo menor)
        if (minIndex != currentIndex) {
            swap(currentIndex, minIndex); // Intercambia con el elemento mínimo de los hijos
            siftDown(minIndex); // Continúa el proceso recursivamente hacia abajo
        }
    }

    //Para testear/depurar
    @Override
    public String toString() {
        // Solo se muestran los elementos hasta size
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb + " (Capacidad: " + getCapacity() + ", Tamaño: " + size + ")";
    }

}