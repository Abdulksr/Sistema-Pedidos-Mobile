import model.Cliente
import model.Pedido
import model.Producto
import service.PizzeriaService
import util.Validacion

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val servicio = PizzeriaService()

    println("=== Sistema de Ventas de Pizza ===")

    // Registro de Cliente
    print("Ingrese su nombre: ")
    val nombre = readlnOrNull()?.ifBlank { "Cliente Anónimo" } ?: "Cliente Anónimo"

    print("Ingrese su dirección: ")
    val direccion = readlnOrNull()?.ifBlank { "Sin Dirección" } ?: "Sin Dirección"

    print("Ingrese su teléfono: ")
    val telefonoEntrada = readlnOrNull()
    val telefono = Validacion.validarTelefono(telefonoEntrada)

    print("Ingrese su correo electrónico: ")
    val emailEntrada = readlnOrNull()
    val email = Validacion.validarEmail(emailEntrada)

    val cliente = Cliente(nombre, telefono, direccion, email)
    cliente.imprimirCliente()

    // Catalogo de productos
    val catalogo = listOf(
        Producto(
            "Pizza Pepperoni", 8500.0, listOf(
                "Masa", "Salsa de Tomate", "Queso Mozzarella",
                "Pepperoni"
            )
        ),
        Producto("Pizza Jamón", 8000.0, listOf("Masa", "Salsa de Tomate", "Queso Mozzarella", "Jamón")),
        Producto(
            "Pizza Vegetariana", 7500.0, listOf(
                "Masa", "Salsa de Tomate", "Queso Mozzarella",
                "Champiñones"
            )
        )
    )

    println("\n--- Menú de Pizzas Disponibles ---")
    catalogo.forEachIndexed { index, prod ->
        println("${index + 1}. ${prod.detallarProductos()}")
    }

    // Seleccion de pizza y cantidad
    val opcionPizza = Validacion.leerCantidad("Seleccione el número de pizza (1-${catalogo.size}): ")
    val pizzaSeleccionada = catalogo.getOrElse(opcionPizza - 1) { catalogo[0] }

    val cantidad = Validacion.leerCantidad("¿Cuántas unidades de ${pizzaSeleccionada.nombre} desea?: ")

    print("¿Desea agregar notas especiales para la cocina? (Enter para omitir): ")
    val notasInput = readlnOrNull()?.takeIf { it.isNotBlank() }
    val notasFinales = notasInput ?: "Sin notas"

    if (!servicio.verificarYActualizarInventario(pizzaSeleccionada, cantidad)) {
        println("\nNo fue posible procesar su pedido por falta de inventario.")
        return
    }

    // Asignacion de respartidor y calculo
    val repartidorAsignado = servicio.obtenerRepartidorDisponible()
    val subtotal = pizzaSeleccionada.precio * cantidad
    val totales = servicio.calcularTotales(subtotal, cantidad)

    val productosPedido = List(cantidad) { pizzaSeleccionada }

    val pedidoActual = Pedido(
        idPedido = 101,
        cliente = cliente,
        productos = productosPedido,
        estado = "En preparación",
        notas = notasFinales,
        repartidor = repartidorAsignado
    )

    // Comprobante del pedido
    println(
        """
        ==================================================
                      RESUMEN DEL PEDIDO #${pedidoActual.idPedido}
        ==================================================
        DATOS DEL CLIENTE:
        - Nombre:    ${cliente.nombre}
        - Contacto:  ${cliente.contacto}
        - Correo:    ${cliente.email}
        - Dirección: ${cliente.direccion}

        DETALLE DEL PRODUCTO:
        - Producto:        ${pizzaSeleccionada.nombre}
        - Precio Unitario: $${pizzaSeleccionada.precio}
        - Cantidad:        $cantidad

        DESGLOSE DE TOTALES:
        - Subtotal:        $${totales["subtotal"]}
        - Descuento (10%): -$${totales["descuento"]}
        - Impuestos (IVA): +$${totales["impuestos"]}
        - TOTAL FINAL:     $${totales["totalFinal"]}

        INFORMACIÓN DE ENTREGA:
        - Estado:          ${pedidoActual.estado}
        - Notas del Chef:  ${pedidoActual.notas}
        - Repartidor:      ${pedidoActual.repartidor?.nombre ?: "Sin repartidor asignado"}
        ==================================================
        """.trimIndent()
    )

    //Simulacion de entrega y Reporte final

    repartidorAsignado?.let {
        pedidoActual.cambiarEstado("En camino")
        it.entregarPedido(pedidoActual.idPedido)
        pedidoActual.cambiarEstado("Listo")
    }

    println("\n================ REPORTE DEL DÍA ================")
    val pedidosDelDia = listOf(
        pedidoActual,
        Pedido(
            102, Cliente("María Paz", "+56988887777", "Providencia 123", "maria@gmail.com"),
            listOf(catalogo[1]), "Listo"
        ),
        Pedido(
            103, Cliente("Juan Pérez", "+56944443333", "Las Condes 456", "juan@gmail.com"),
            listOf(catalogo[2]), "En preparación"
        )
    )

    println("--- Todos los pedidos registrados ---")
    for (ped in pedidosDelDia) {
        println("Pedido #${ped.idPedido} | Cliente: ${ped.cliente.nombre} | Pizzas: ${ped.productos.size} | Estado: ${ped.estado}")
    }

    println("\n--- Pedidos Completados ---")
    val completados = pedidosDelDia.filter { it.estado == "Listo" }
    completados.forEach { println("Pedido #${it.idPedido} de ${it.cliente.nombre}") }

    println("\n--- Pedidos Pendientes ---")
    val pendientes = pedidosDelDia.filter { it.estado != "Listo" }
    pendientes.forEach { println("Pedido #${it.idPedido} de ${it.cliente.nombre} (${it.estado})") }
    println("==================================================")


}