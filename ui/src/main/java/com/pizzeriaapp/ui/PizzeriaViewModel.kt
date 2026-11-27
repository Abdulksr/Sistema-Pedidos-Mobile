package com.pizzeriaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pizzeriaapp.data.model.Cliente
import com.pizzeriaapp.data.model.Pedido
import com.pizzeriaapp.data.model.Producto
import com.pizzeriaapp.data.model.promociones.ComboFamiliar
import com.pizzeriaapp.data.model.promociones.PizzaPromocional
import com.pizzeriaapp.data.service.PizzeriaService
import com.pizzeriaapp.utils.Validacion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PantallaApp(val titulo: String) {
    RESUMEN("Resumen General"),
    CLIENTES("Gestión de Clientes"),
    PRODUCTOS("Catálogo de Productos"),
    PEDIDOS("Registro y Pedidos")
}

class PizzeriaViewModel : ViewModel() {

    private val _pantallaActual = MutableLiveData(PantallaApp.RESUMEN)
    val pantallaActual: LiveData<PantallaApp> = _pantallaActual

    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean> = _cargando

    private val _mensajeProgreso = MutableLiveData("")
    val mensajeProgreso: LiveData<String> = _mensajeProgreso

    // Carga inicial simulada al abrir la app ("Generando resumen...")
    init {
        recargarResumen()
    }

    fun recargarResumen() {
        viewModelScope.launch {
            _cargando.value = true
            _mensajeProgreso.value = "Generando resumen del sistema..."
            delay(1200)
            _cargando.value = false
            _mensajeProgreso.value = ""
        }
    }

    fun navegarA(destino: PantallaApp) {
        if (_pantallaActual.value == destino) return
        viewModelScope.launch {
            _cargando.value = true
            _mensajeProgreso.value = "Cargando ${destino.titulo.lowercase()}..."
            delay(800)
            _pantallaActual.value = destino
            _cargando.value = false
            _mensajeProgreso.value = ""
        }
    }

    private val servicio = PizzeriaService()

    val catalogo: List<Producto> = listOf(
        Producto(
            "Pizza Pepperoni", 8500.0, listOf(
                "Masa", "Salsa de Tomate", "Queso Mozzarella", "Pepperoni"
            )
        ),
        Producto(
            "Pizza Jamón",
            8000.0,
            listOf("Masa", "Salsa de Tomate", "Queso Mozzarella", "Jamón")
        ),
        PizzaPromocional(
            "Pizza Promo Suprema", 9500.0, listOf(
                "Masa", "Salsa de Tomate", "Queso Mozzarella ", " Pepperoni ", " Champiñones "
            )
        ),
        ComboFamiliar(
            "Mega Combo Familiar", 14000.0, listOf(
                "Masa", "Salsa de Tomate", "Queso Mozzarella", "Jamón", "Pepperoni"
            )
        )
    )

    // Datos iniciales para que la pantalla ya muestre un resumen real al abrir la app
    private
    val clienteInicial = Cliente(
        "Abdul", "+56 (912) 345-678", "Av. Apoquindo 123",
        "abdul@duocuc.cl"
    )

    private
    val pedidoInicial = Pedido(
        idPedido = 101,
        cliente = clienteInicial,
        productos = listOf(catalogo[0], catalogo[2]),
        estado = "En preparación",
        repartidor = servicio.obtenerRepartidorDisponible()
    )

    // LiveData privados (mutables) y públicos (solo lectura)
    private
    val _clientes =
        MutableLiveData<List<Cliente>>(listOf(clienteInicial))
    val clientes: LiveData<List<Cliente>> = _clientes

    private
    val _pedidos = MutableLiveData<List<Pedido>>(listOf(pedidoInicial))
    val pedidos: LiveData<List<Pedido>> = _pedidos

    private val _ultimoCliente = MutableLiveData<String>(clienteInicial.nombre)
    val ultimoCliente: LiveData<String> = _ultimoCliente

    private val _mensajeEstado = MutableLiveData<String>("Sistema listo.")
    val mensajeEstado: LiveData<String> = _mensajeEstado

    // Operación asíncrona con Kotlin Coroutines (viewModelScope.launch)
    fun registrarClienteYPedido(
        nombre: String,
        direccion: String,
        telefono: String,
        email: String,
        producto: Producto,
        cantidad: Int
    ) {
        viewModelScope.launch {
            if (nombre.isBlank()) {
                _mensajeEstado.value = "Error: Ingresa el nombre del cliente."
                return@launch
            }
            if (direccion.isBlank()) {
                _mensajeEstado.value = "Error: Ingresa la dirección de entrega."
                return@launch
            }
            val telFormateado = Validacion.formatearTelefonoSiValido(telefono)
            if (telFormateado == null) {
                _mensajeEstado.value =
                    "Error: Ingresa un teléfono válido (mínimo 11 dígitos, ej.56912345678)."
                return@launch
            }
            if (!Validacion.esEmailValido(email)) {
                _mensajeEstado.value = "Error: Correo inválido (ej. nombre@dominio.com)."
                return@launch
            }
            if (!Validacion.esCantidadValida(cantidad)) {
                _mensajeEstado.value = "Error: La cantidad debe ser un número entre 1 y 100."
                return@launch
            }

            _cargando.value = true
            try {
                _mensajeProgreso.value = "Guardando nuevo pedido..."
                delay(1000)

                val nuevoCliente = Cliente(
                    nombre = nombre.trim(),
                    contacto = telFormateado,
                    direccion = direccion.trim(),
                    email = email.trim()
                )

                val listaActualClientes = _clientes.value.orEmpty()
                val listaActualPedidos = _pedidos.value.orEmpty()

                val nuevoPedido = Pedido(
                    idPedido = 101 + listaActualPedidos.size,
                    cliente = nuevoCliente,
                    productos = List(cantidad) { producto },
                    estado = "En preparación",
                    repartidor = servicio.obtenerRepartidorDisponible()
                )

                // Evita clientes duplicados usando equals() de Cliente
                _clientes.value = if (nuevoCliente !in listaActualClientes) {
                    listaActualClientes + nuevoCliente
                } else {
                    listaActualClientes
                }
                _pedidos.value = listaActualPedidos + nuevoPedido
                _ultimoCliente.value = nuevoCliente.nombre
                _mensajeEstado.value =
                    "Pedido #${nuevoPedido.idPedido} registrado para ${nuevoCliente.nombre}."
            } finally {
                _cargando.value = false
                _mensajeProgreso.value = ""
            }
        }
    }
}