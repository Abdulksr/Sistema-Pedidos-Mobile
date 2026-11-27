# 🍕 PizzeriaApp — Aplicación Móvil Android en Kotlin (Multimódulo)

> **Evaluación Sumativa 2 — Experiencia 2 (Semana 6)**  
> **Asignatura:** Desarrollo de Aplicaciones Móviles I (`PMY2201`)  
> **Institución:** Duoc UC  
> **Actividad:** *"Avanzando hacia Android Studio"*  
> **Autor:** Abdul (`Abdulksr`)  

---

## 📋 Descripción del Proyecto

**PizzeriaApp** es una aplicación móvil Android desarrollada en **Kotlin** con **Android Studio** y **Jetpack Compose (Material Design 3)**. Implementa una **arquitectura multimódulo con Gradle**, gestión reactiva de estado con **ViewModel + LiveData**, operaciones asíncronas con **Kotlin Coroutines**, **animaciones Fade In/Out (`AnimatedVisibility`)**, **menú desplegable con íconos (`DropdownMenu`)** y **notificaciones de progreso dinámicas (`CircularProgressIndicator`)**.

---

## 🏗️ Arquitectura Multimódulo (Gradle Kotlin DSL)

El proyecto separa responsabilidades en **4 módulos Gradle** declarados en `settings.gradle.kts` con flujo de dependencias unidireccional:

```text
:app  ──▶  :ui  ──▶  :data  ──▶  :utils
```

```text
PizzeriaApp/
├── gradle/
│   └── libs.versions.toml                  <- Catálogo centralizado de librerías y versiones
├── settings.gradle.kts                     <- Inclusión de :app, :utils, :data y :ui
├── utils/                                  <- Módulo de Utilidades (com.pizzeriaapp.utils)
│   └── src/main/java/com/pizzeriaapp/utils/
│       ├── annotation/Promocionable.kt     <- Anotación personalizada @Promocionable
│       ├── ReflectionUtil.kt               <- Inspección dinámica por Reflection
│       └── Validacion.kt                   <- Validaciones puras Android (Regex correo, teléfono, rango 1..100)
├── data/                                   <- Módulo de Datos y Dominio (com.pizzeriaapp.data)
│   └── src/main/java/com/pizzeriaapp/data/
│       ├── model/                          <- Usuario, Cliente, Repartidor, Producto, Pedido y Promociones (Date)
│       └── service/PizzeriaService.kt      <- Inventario (MutableMap), when y repartidores
├── ui/                                     <- Módulo de Interfaz y Estado (com.pizzeriaapp.ui)
│   └── src/main/java/com/pizzeriaapp/ui/
│       ├── PizzeriaViewModel.kt            <- PantallaApp (enum), ViewModel, LiveData, Coroutines y estados de carga
│       └── PizzeriaScreen.kt               <- TopAppBar + DropdownMenu + AnimatedVisibility + ProgressIndicator
└── app/                                    <- Módulo Principal Android (com.pizzeriaapp)
    └── src/main/java/com/pizzeriaapp/
        ├── MainActivity.kt                 <- Punto de entrada Jetpack Compose
        └── ui/theme/                       <- Tema Material Design 3
```

---

## 🚀 Funcionalidades de la Semana 6 (Sumativa)

### 1. ✨ Animaciones Fade In / Fade Out (`AnimatedVisibility`)
- **Fade In al iniciar:** `LaunchedEffect(Unit)` activa `pantallaIniciada = true` con `fadeIn(animationSpec = tween(600))`.
- **Fade Out / Fade In al navegar:** Las transiciones entre las 4 pantallas (`RESUMEN`, `CLIENTES`, `PRODUCTOS`, `PEDIDOS`) y entre el estado de carga y contenido se controlan con `AnimatedVisibility` (`fadeIn` / `fadeOut`).

### 2. ☰ Menú Interactivo Desplegable (`DropdownMenu` en `TopAppBar`)
- Barra superior Material 3 (`TopAppBar`) con botón de recarga (`Icons.Default.Refresh`) y botón de menú (`Icons.Default.Menu`).
- `DropdownMenu` con íconos representativos (`Home`, `Person`, `List`, `ShoppingCart`) que navegan dinámicamente entre **Resumen General**, **Gestión de Clientes**, **Catálogo de Productos** y **Registro y Pedidos**.

### 3. ⏳ Notificaciones de Progreso (`CircularProgressIndicator`)
- Controladas mediante `LiveData` (`cargando: LiveData<Boolean>`, `mensajeProgreso: LiveData<String>`) y **Kotlin Coroutines (`viewModelScope.launch`)** con cierre seguro en bloque `try / finally`.
- Mensajes contextuales en pantalla:
  - *"Generando resumen del sistema..."* (al abrir o recargar)
  - *"Cargando gestión de clientes / catálogo de productos / registro y pedidos..."* (al navegar)
  - *"Guardando nuevo pedido..."* (al registrar un nuevo pedido)

---

## 🧪 Validación y Pruebas

| Caso Probado | Acción Ejecutada | Resultado Verificado |
| :--- | :--- | :--- |
| **Compilación Multimódulo** | `./gradlew assembleDebug` en `:utils`, `:data`, `:ui`, `:app` | ✅ `BUILD SUCCESSFUL` sin errores |
| **Carga Inicial y Fade In** | Abrir la app en AVD (API 31+) | ✅ Muestra *"Generando resumen del sistema..."* con `CircularProgressIndicator` y luego entra el Resumen con *Fade In* |
| **Menú y Navegación** | Abrir `DropdownMenu` y seleccionar Clientes / Productos / Pedidos | ✅ Transición suave *Fade Out → Carga → Fade In* con actualización de título en `TopAppBar` |
| **Registro Dinámico** | Ingresar cliente válido en *Registro y Pedidos* y pulsar *Guardar Pedido* | ✅ Muestra *"Guardando nuevo pedido..."*, agrega el pedido a la lista y actualiza el Resumen General |
| **Validación de Errores** | Ingresar nombre vacío o correo sin `@` | ✅ Muestra mensaje de error en pantalla sin colapsar |

---

## ▶️ Cómo Ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Abdulksr/Sistema-Pedidos-Mobile.git
   ```
2. Abrir en **Android Studio** y esperar a que termine **Gradle Sync**.
3. Ejecutar sobre un **AVD (API 31+)** o dispositivo físico presionando **Run (▶)**.

---

*Proyecto desarrollado con fines exclusivamente académicos y formativos para Duoc UC.*
