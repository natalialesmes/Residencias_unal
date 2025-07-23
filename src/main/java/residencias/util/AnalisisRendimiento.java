package residencias.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import residencias.datos.AlmacenamientoArchivos;
import residencias.estructuras.Estudiante;
import residencias.estructuras.HashTable;
import residencias.estructuras.MinHeap;
import static residencias.util.GeneradorMockData.generarEstudiantes;

//Analizar el rendimiento como en el Taller
public class AnalisisRendimiento {

    private static <TSetup> void runPerformanceTest(
            String description,
            int size,
            Supplier<TSetup> setupOperation,
            Consumer<TSetup> testOperation) {

        long totalTime = 0;
        for (int i = 0; i < 5; i++) {
            TSetup setupData = setupOperation.get(); //Get setup
            Instant start = Instant.now();
            testOperation.accept(setupData); //Hacer el test
            Instant end = Instant.now();
            totalTime += Duration.between(start, end).toMillis();
        }
        System.out.printf("Tiempo promedio de %s de %d estudiantes: %d milisegundos\n",
                description, size, totalTime / 5);
 
        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv", true))) {
            writer.printf("%s,%d,%d\n", description, size, totalTime / 5);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }

                
    }


    public static void main(String[] args) {
        int startSize = 100000;
        int endSize = 1200000;
        int increment = 100000;

        System.out.println("Tamaño inicial: " + startSize + ", Tamaño final: " + endSize + ", Incremento: " + increment);

        for (int size = startSize; size <= endSize; size += increment) {
            final int currentSize = size;
            System.out.println("\n---" + size + " estudiantes ---");

            // Generar y persistir estudiantes
            LinkedList<Estudiante> estudiantes = generarEstudiantes(currentSize);
            AlmacenamientoArchivos archivos = new AlmacenamientoArchivos();
            archivos.guardar(estudiantes); // Persistencia opcional en tiempo de ejecución

            // Test Insert
//            runPerformanceTest(
//                    "INSERCIÓN",
//                    size,
//                    () -> { // Setup: Crear estudiantes
//                        return generarEstudiantes(currentSize);
//                    },
//                    (studentsToInsert) -> { // Operation: Insertar estudiantes en el heap
//                        MinHeap<Estudiante> heap = new MinHeap<>();
//                        for (Estudiante est : studentsToInsert) {
//                            heap.insert(est);
//                        }
//                    }
//            );
//
//            // Test ExtractMin
//            runPerformanceTest(
//                    "EXTRAER MIN",
//                    size,
//                    () -> { // Setup
//                        MinHeap<Estudiante> heap = new MinHeap<>();
//                        for (Estudiante est : generarEstudiantes(currentSize)) {
//                            heap.insert(est);
//                        }
//                        return heap; // Retornar el heap con los elementos
//                    },
//                    (heapToExtract) -> { // Extraer
//                        while (!heapToExtract.isEmpty()) {
//                            heapToExtract.extractMin();
//                        }
//                    }
//            );
//
//            // Test PeekMin
//            runPerformanceTest(
//                    "PEEK MIN",
//                    size,
//                    () -> { // Setup
//                        MinHeap<Estudiante> heap = new MinHeap<>();
//                        for (Estudiante est : generarEstudiantes(currentSize)) {
//                            heap.insert(est);
//                        }
//                        return heap; // Retornar el heap con los elementos
//                    },
//                    (heapToPeek) -> { //Hacer el peek
//                        for (int k = 0; k < currentSize; k++) { // Hacer n peeks aunque sean redundantes
//                            heapToPeek.peekMin();
//                        }
//                    }
//            );
//
//
//
//            // Test Remove
//            runPerformanceTest(
//                    "ELIMINAR",
//                    currentSize,
//                    () -> { // Setup: Crear estudiantes
//                        MinHeap<Estudiante> heap = new MinHeap<>();
//                        LinkedList<Estudiante> allStudents = generarEstudiantes(currentSize); // Todos los estudiantes
//
//                        // insertar los estudiantes
//                        for (Estudiante est : allStudents) {
//                            heap.insert(est);
//                        }
//
//                        LinkedList<Estudiante> studentsToRemoveInOrder = new LinkedList<>(allStudents);
//
//                        return new Object[]{heap, studentsToRemoveInOrder}; // Return heap
//                    },
//                    (setupResult) -> { // Hacer las eliminaciones
//                        @SuppressWarnings("unchecked")
//                        MinHeap<Estudiante> heapToModify = (MinHeap<Estudiante>) ((Object[]) setupResult)[0];
//                        @SuppressWarnings("unchecked")
//                        LinkedList<Estudiante> studentsToActuallyRemove = (LinkedList<Estudiante>) ((Object[]) setupResult)[1];
//
//                        for (Estudiante estToRemove : studentsToActuallyRemove) {
//                            heapToModify.remove(estToRemove);
//                        }
//                    }
//            );


            runPerformanceTest(
                    "ADD",
                    currentSize,
                    () -> { // Setup: Generar los estudiantes a añadir
                        return generarEstudiantes(currentSize);
                    },
                    (studentsToAdd) -> { // Operation: Añadir todos los estudiantes a una nueva HashTable
                        HashTable<String, Estudiante> table = new HashTable<>();
                        for (Estudiante est : studentsToAdd) {
                            table.add(est.getId(), est);
                        }
                    }
            );

            runPerformanceTest(
                    "FIND",
                    currentSize,
                    () -> { // Setup: Insertar todos los estudiantes en la tabla y preparar la lista de búsqueda
                        HashTable<String, Estudiante> table = new HashTable<>();
                        LinkedList<Estudiante> allStudents = generarEstudiantes(currentSize);
                        for (Estudiante est : allStudents) {
                            table.add(est.getId(), est);
                        }

                        return new Object[]{table, allStudents}; // Retornar la tabla y la lista para buscar
                    },
                    (setupResult) -> { // Operation: Buscar todos los estudiantes en la tabla
                        @SuppressWarnings("unchecked")
                        HashTable<String, Estudiante> table = (HashTable<String, Estudiante>) ((Object[]) setupResult)[0];
                        @SuppressWarnings("unchecked")
                        LinkedList<Estudiante> studentsToFind = (LinkedList<Estudiante>) ((Object[]) setupResult)[1];

                        for (Estudiante est : studentsToFind) {
                            table.find(est.getId()); // Realizar la búsqueda
                        }
                    }
            );


            runPerformanceTest(
                    "REMOVE",
                    currentSize,
                    () -> { // Setup: Insertar todos los estudiantes en la tabla y preparar la lista de eliminación
                        HashTable<String, Estudiante> table = new HashTable<>();
                        LinkedList<Estudiante> allStudents = generarEstudiantes(currentSize);
                        for (Estudiante est : allStudents) {
                            table.add(est.getId(), est);
                        }
                        return new Object[]{table, allStudents}; // Retornar la tabla y la lista para eliminar
                    },
                    (setupResult) -> { // Operation: Eliminar todos los estudiantes de la tabla
                        @SuppressWarnings("unchecked")
                        HashTable<String, Estudiante> table = (HashTable<String, Estudiante>) ((Object[]) setupResult)[0];
                        @SuppressWarnings("unchecked")
                        LinkedList<Estudiante> studentsToRemove = (LinkedList<Estudiante>) ((Object[]) setupResult)[1];

                        for (Estudiante est : studentsToRemove) {
                            table.remove(est.getId()); // Realizar la eliminación
                        }
                    }
            );


        }
    }
}







