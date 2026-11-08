# 🍕 Sistema de Gestión de Pizzería — Kotlin

> **Evaluación Sumativa 1 (100% Logrado)**  
> **Asignatura:** Desarrollo de Aplicaciones Móviles I (`PMY2201`)  
> **Institución:** Duoc UC  
> **Semana 3:** *"Creando soluciones con Kotlin"*  
> **Autor:** Abdul (`Abdulksr`)  

---

## 📋 Descripción del Proyecto

Aplicación de consola desarrollada en **Kotlin** para la gestión operativa integral de una pizzería. Este proyecto representa la culminación de la primera unidad formativa y sumativa, integrando conceptos avanzados del lenguaje como **Programación Orientada a Objetos**, **Metaprogramación (Anotaciones y Reflection)**, **Sobrecarga de Operadores**, **Declaraciones de Desestructuración**, **Expresiones Regulares (Regex)**, **Rangos de Números y Fechas**, y **Evaluación de Igualdad con Colecciones Set**.

---

## 🚀 Requerimientos de la Pauta Sumativa Cumplidos

### 1. 🔤 Expresiones Regulares (Regex) y Rangos (Ranges) — 20 Puntos
- **Validación Estricta de Correo:** Expresión regular estándar (`^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$`) evaluada en un bucle interactivo que rechaza correos inválidos y solicita reingreso.
- **Formateo Uniforme de Teléfono:** Procesamiento con Regex para transformar cualquier entrada numérica en el formato estándar `+XX (XXX) XXX-XXXX`.
- **Rangos Numéricos (`1..100`):** Control de cantidades permitidas mediante el operador `in` sobre un `IntRange`, respaldado con bloques `try-catch` para capturar excepciones de conversión.
- **Rangos de Fechas (`LocalDate`):** Validación de temporada promocional evaluando si la fecha actual cae dentro del rango `fechaInicio..fechaFin` del producto.

### 2. 🏷️ Anotaciones Personalizadas y Reflection — 20 Puntos
- **Anotación `@Promocionable`:** Creada con retención en tiempo de ejecución (`AnnotationRetention.RUNTIME`) y aplicada sobre múltiples entidades del dominio (`PizzaPromocional`, `ComboFamiliar`).
- **Inspección Dinámica (`ReflectionUtil`):** Análisis reflexivo en tiempo de ejecución que detecta anotaciones, lista métodos disponibles y propiedades declaradas sin dependencias externas.

### 3. ➕ Sobrecarga de Operadores (Operator Overloading) — 15 Puntos
- **Operador `+` en `Pedido` (`operator fun plus`):** Permite combinar dos pedidos (`pedidoA + pedidoB`) fusionando sus listas de productos, uniendo notas y recalculando totales con descuentos e IVA de forma automática.
- **Operador `==` en `Producto` (`operator fun equals`):** Compara igualdad de negocio según nombre y categoría, ignorando mayúsculas/minúsculas.

### 4. 🧩 Declaraciones de Desestructuración — 10 Puntos
- **Desestructuración en `Cliente`:** Extracción directa mediante componentes:
  ```kotlin
  val (nombre, email, telefono) = cliente
  ```
- **Desestructuración en `Pedido`:** Extracción de componentes esenciales:
  ```kotlin
  val (cliente, productos, total) = pedidoActual
  ```

### 5. 🛡️ Igualdad de Objetos y Control de Duplicados con `Set` — 15 Puntos
- Sobrescritura consistente de **`equals()`** y **`hashCode()`** en `Cliente` y `Producto`.
- Uso de **`mutableSetOf<Cliente>()`** que garantiza que dos instancias con los mismos atributos clave no se dupliquen en el sistema.

### 6. 📊 Resumen Ejecutivo y Reporte del Día — 10 Puntos
- Salida clara y detallada en consola que evidencia la desestructuración, los pedidos combinados con `+`, la detección por reflection y el reporte diario con desglose financiero.

---

## 📂 Arquitectura del Proyecto

```text
src/
├── annotation/
│   └── Promocionable.kt        <- Anotación personalizada @Promocionable
├── model/
│   ├── Usuario.kt              <- Clase base abierta (Herencia)
│   ├── Cliente.kt              <- Desestructuración y Equals/HashCode
│   ├── Repartidor.kt           <- Polimorfismo con entregarPedido()
│   ├── Producto.kt             <- Categoría, Equals/HashCode y ==
│   ├── Pedido.kt               <- Sobrecarga de operador + y desestructuración
│   └── promociones/
│       ├── ComboFamiliar.kt    <- Clase anotada con @Promocionable
│       └── PizzaPromocional.kt <- Rango de fechas y @Promocionable
├── service/
│   └── PizzeriaService.kt      <- Inventario (Map), when y repartidores (List)
├── util/
│   ├── ReflectionUtil.kt       <- Metaprogramación y análisis dinámico
│   └── Validacion.kt           <- Regex, Ranges (1..100) y try-catch
└── Main.kt                     <- Punto de entrada e integración sumativa
```

---

## ▶️ Ejecución del Proyecto

1. Abrir en **IntelliJ IDEA**.
2. Verificar JDK 17 o superior en `File -> Project Structure`.
3. Ejecutar `src/Main.kt` presionando `Shift + F10` o el botón **Run**.
