# 🍕 Sistema de Gestión de Pizzería — Kotlin

> **Proyecto Académico / Aplicación Ficticia**  
> **Asignatura:** Desarrollo de Aplicaciones Móviles I (`PMY2201`)  
> **Institución:** Duoc UC  
> **Semana 2:** Actividad Formativa — *"Aplicando utilidades de Kotlin"*  
> **Autor:** Abdul (`Abdulksr`)  

---

## 📋 Descripción del Proyecto

Este proyecto es una aplicación de consola desarrollada en **Kotlin** como parte de una actividad formativa académica para **Duoc UC**. Simula la operación diaria de una pizzería local para optimizar el registro de clientes, gestión de pedidos, control de inventario de ingredientes por receta y despacho con repartidores.

El propósito principal es aplicar y consolidar los fundamentos y utilidades modernas del lenguaje Kotlin (POO, herencia, colecciones avanzadas, lambdas, flujos de control y *Null Safety*).

---

## 🚀 Características Principales y Requerimientos Cumplidos

### 1. 🏛️ Programación Orientada a Objetos y Herencia (POO)
- **Clase Base `Usuario`:** Define atributos comunes (`nombre`, `contacto`) y está abierta (`open`) para especialización.
- **Especialización `Cliente`:** Extiende de `Usuario` e incorpora `direccion` y `email`.
- **Especialización `Repartidor`:** Extiende de `Usuario`, maneja disponibilidad e incorpora el método de comportamiento `entregarPedido(idPedido)`.
- **Entidades de Negocio:**
  - `Producto`: Representa pizzas con `nombre`, `precio` e `ingredientes` (`List<String>`), incluyendo el método `detallarProductos()`.
  - `Pedido`: Asocia al `Cliente`, la lista de `Producto`, notas opcionales (`String?`) y el `Repartidor?` asignado.

### 2. 🗃️ Colecciones Avanzadas y Métodos Funcionales
- **Inventario con `MutableMap`:** Registro dinámico de stock de ingredientes (clave: nombre ingrediente, valor: cantidad disponible).
- **Asignación con `.find`:** Búsqueda funcional sobre `listOf<Repartidor>` para encontrar y asignar el primer repartidor disponible.
- **Reporte Diario con `.filter`:** Separación de pedidos completados (`Listo`) vs pedidos pendientes (`En preparación`) usando operaciones funcionales sobre colecciones.
- **Cálculo con `.sumOf`:** Suma funcional de subtotales a partir de la lista de productos del pedido.

### 3. 🔀 Flujos de Control y Expresiones
- **Evaluación de Stock con `when`:** Verificación automática de la receta del producto contra el inventario. Si faltan insumos, el sistema informa exactamente qué ingredientes son insuficientes y genera los pasos para su reabastecimiento.
- **Descuentos e Impuestos:** Aplicación de un **10% de descuento** si el pedido supera las 3 unidades, más el desglose del **19% de IVA**.

### 4. 🛡️ Robustez, Validaciones y Null Safety
- **Validación de Cantidades (`Validacion.leerCantidad`):** Control estricto mediante bucle interactivo que rechaza números negativos o cero.
- **Validación con Expresiones Regulares (`Regex`):** Formateo y validación de números telefónicos y correos electrónicos con asignación de valores seguros predeterminados ante entradas erróneas.
- **Operadores Seguros:** Empleo de `?.`, operador Elvis `?:` para notas opcionales (`"Sin notas"`) y bloque `.let { ... }` para ejecución condicional no nula.

---

## 📂 Estructura del Proyecto

```text
src/
├── model/
│   ├── Usuario.kt       <- Clase base abierta (Herencia)
│   ├── Cliente.kt       <- Especialización de Usuario
│   ├── Repartidor.kt    <- Especialización de Usuario con entregarPedido()
│   ├── Producto.kt      <- Modelo de pizzas e ingredientes
│   └── Pedido.kt        <- Modelo de pedidos, notas y estados
├── service/
│   └── PizzeriaService.kt <- Gestión de inventario (Map), when y repartidores (List)
├── util/
│   └── Validacion.kt    <- Utilitarios de validación (Regex, cantidades > 0)
└── Main.kt              <- Flujo principal, comprobante multilínea y reporte
```

---

## 🛠️ Tecnologías y Requisitos

- **Lenguaje:** Kotlin 1.9+ / 2.0+
- **JDK:** Java OpenJDK 17 o 21
- **IDE Recomendado:** IntelliJ IDEA (Community o Ultimate)

---

## ▶️ Instrucciones de Ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
   ```
2. Abrir el proyecto en **IntelliJ IDEA**.
3. Asegurarse de tener configurado un Project SDK (JDK 17 o superior) en `File -> Project Structure`.
4. Navegar a `src/Main.kt` y presionar el botón verde **Run** (o `Shift + F10`).
5. Seguir las instrucciones interactivas en la consola.

---

*Proyecto desarrollado con fines exclusivamente pedagógicos y formativos para Duoc UC.*
