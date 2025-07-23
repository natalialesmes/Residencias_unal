# 🏠 Sistema de Asignación de Residencias UNAL

> Proyecto para la asignación eficiente y priorizada de residencias universitarias en la Universidad Nacional de Colombia.

---

## 📌 Objetivo

Este sistema permite gestionar solicitudes de residencia por parte de estudiantes, dando prioridad a quienes tienen menor puntaje socioeconómico. El objetivo es garantizar una asignación justa y eficiente mediante el uso de estructuras de datos.

---

## 👥 Integrantes del equipo

| 👤 Nombre                       | 🎯 Rol asignado                                                 |
|---------------------------------|-----------------------------------------------------------------|
| Leidy Juliana Vargas Moreno     | Diseñadora de interfaz, diseñadora de requerimientos, redactora |
| Natalia Sofia Lesmes Romero     | Redactora, documentadora                                        |
| Emmanuel Bonilla Mitrotti       | Diseñador del flujo del sistema, redactor                       |
| Yeiner Zapata Vallejo           | Redactor, verificador                                           |
| Jonathan Felipe López           | Coordinador general, diseñador de lógica y estruturas, redactor |

---

## 🎯 Funcionalidades principales

- 📝 Registro de estudiantes con ID, nombre y puntaje socioeconómico
- 🔍 Consulta directa de estudiantes por ID
- ✏️ Modificación del puntaje con actualización automática
- ❌ Eliminación de registros
- 📋 Listado ordenado de estudiantes (por prioridad)
- 🏆 Asignación de cupos empezando por los estudiantes con menores puntajes socioeconómico

---

## 🧠 Estructuras de datos clave

| Estructura | Uso en el sistema    | Justificación                                                      |
|------------|----------------------|--------------------------------------------------------------------|
| `HashMap`  | Acceso rápido por ID | Permite buscar, actualizar o eliminar estudiantes instantáneamente |
| `MinHeap`  | Asignación de cupos  | Extrae al estudiante de menor puntaje socioeconómico               |
| `AVL`  | Modificación de puntaje  | Reorganiza los puntajes que se modifiquen para mantener una complejidad capetable               |




