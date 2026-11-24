# 🍕 PizzeriaApp — Aplicación Móvil Android en Kotlin (Multimódulo)

> **Proyecto Académico / Aplicación Ficticia**  
> **Asignatura:** Desarrollo de Aplicaciones Móviles I (`PMY2201`)  
> **Institución:** Duoc UC  
> **Experiencia 2 (Semanas 4 y 5):** *"Integrando Kotlin en Android Studio"*  
> **Autor:** Abdul (`Abdulksr`)  

---

## 📋 Descripción del Proyecto

**PizzeriaApp** es una aplicación móvil Android desarrollada en **Kotlin** con **Android Studio** y **Jetpack Compose (Material Design 3)**. Representa la evolución a entorno móvil real del sistema de gestión de una pizzería local, aplicando una **arquitectura multimódulo con Gradle**, manejo reactivo de estado mediante **ViewModel + LiveData** y operaciones asíncronas con **Kotlin Coroutines**.

---

## 🏗️ Arquitectura Multimódulo (Gradle Kotlin DSL)

El proyecto está organizado en **4 módulos Gradle** declarados en `settings.gradle.kts` con dependencias unidireccionales (sin ciclos):

```text
:app  ──▶  :ui  ──▶  :data  ──▶  :utils
```

```text
PizzeriaApp/
├── gradle/
│   └── libs.versions.toml         <- Catálogo centralizado de versiones y librerías
├── settings.gradle.kts            <- Definición de módulos (:app, :utils, :data, :ui)
├── utils/                         <- Módulo de Utilidades (com.pizzeriaapp.utils)
│   └── src/main/java/com/pizzeriaapp/utils/
│       ├── annotation/Promocionable.kt  <- Anotación personalizada @Promocionable
│       ├── ReflectionUtil.kt            <- Inspección dinámica por Reflection
│       └── Validacion.kt                <- Validaciones con Regex y formateo de teléfono
├── data/                          <- Módulo de Datos y Dominio (com.pizzeriaapp.data)
│   └── src/main/java/com/pizzeriaapp/data/
│       ├── model/
│       │   ├── Usuario.kt               <- Clase base abierta (Herencia)
│       │   ├── Cliente.kt               <- Desestructuración, equals y hashCode
│       │   ├── Repartidor.kt            <- Especialización con entregarPedido()
│       │   ├── Producto.kt              <- Modelo de productos y categorías
│       │   ├── Pedido.kt                <- Cálculo de totales, operador + y desestructuración
│       │   └── promociones/
│       │       ├── ComboFamiliar.kt     <- Producto @Promocionable con rango de fechas (Date)
│       │       └── PizzaPromocional.kt  <- Producto @Promocionable con rango de fechas (Date)
│       └── service/
│           └── PizzeriaService.kt       <- Inventario (MutableMap), when y repartidores
├── ui/                            <- Módulo de Interfaz y Estado (com.pizzeriaapp.ui)
│   └── src/main/java/com/pizzeriaapp/ui/
│       ├── PizzeriaViewModel.kt         <- ViewModel + LiveData + Coroutines (viewModelScope)
│       └── PizzeriaScreen.kt            <- Pantalla en Jetpack Compose (Material 3)
└── app/                           <- Módulo Principal Android (com.pizzeriaapp)
    └── src/main/java/com/pizzeriaapp/
        ├── MainActivity.kt              <- Punto de entrada que conecta ViewModel y Compose
        └── ui/theme/                    <- Tema visual Material 3 (Color, Theme, Type)
```

---

## 🚀 Funcionalidades Implementadas

- **Resumen Dinámico en Pantalla:** Muestra en tiempo real el total de clientes registrados, total de pedidos realizados y el nombre del último cliente registrado, actualizándose automáticamente vía `LiveData.observeAsState()`.
- **Registro Interactivo de Clientes y Pedidos:** Formulario en Jetpack Compose con validaciones de correo (`Regex`) y formateo de teléfono mediante el módulo `:utils`, procesado de forma asíncrona con **Kotlin Coroutines (`viewModelScope.launch`)**.
- **Visualización Dinámica de Pedidos:** Lista reactiva en `LazyColumn` con claves únicas (`key = { it.idPedido }`) mostrando cliente, contacto, cantidad de productos, total calculado con IVA/descuento y estado del pedido.
- **Compatibilidad Android (`minSdk = 24`):** Uso de `java.util.Date` con rangos de fechas (`in fechaInicio..fechaFin`) para compatibilidad desde Android 7.0 en adelante.

---

## 🛠️ Stack Técnico

- **Lenguaje:** Kotlin (`2.2.10`)
- **UI:** Jetpack Compose + Material Design 3 + `runtime-livedata`
- **Arquitectura:** AndroidX `ViewModel` + `LiveData`
- **Asincronía:** `kotlinx-coroutines-core` / `kotlinx-coroutines-android`
- **Build System:** Gradle Kotlin DSL + Version Catalog (`libs.versions.toml`)
- **SDK:** `minSdk = 24` | `targetSdk = 37`

---

## ▶️ Cómo Ejecutar el Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Abdulksr/Sistema-Pedidos-Mobile.git
   ```
2. Abrir la carpeta del proyecto en **Android Studio**.
3. Esperar a que **Gradle Sync** descargue las dependencias de los 4 módulos.
4. Seleccionar un **Android Virtual Device (AVD)** (API 31 o superior) o un dispositivo físico.
5. Presionar **Run (▶)** para compilar e instalar la aplicación.

---

*Proyecto desarrollado con fines exclusivamente académicos y formativos para Duoc UC.*
