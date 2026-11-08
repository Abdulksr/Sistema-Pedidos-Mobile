import model.Cliente
import model.Pedido
import model.Producto
import model.promociones.ComboFamiliar
import model.promociones.PizzaPromocional
import service.PizzeriaService
import util.ReflectionUtil
import util.Validacion

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val servicio = PizzeriaService()
    val reflector = ReflectionUtil()

    println("=== Sistema de Ventas de Pizza ===")

    //Reflection y anotaciones
    println("\n--- INSPECCIÓN DINÁMICA CON REFLECTION Y ANOTACIONES ---")
    val pizzaDemoPromo = PizzaPromocional("Promo Familiar", 12000.0, listOf("Masa", "Queso", "Pepperoni"))
    val comboDemo = ComboFamiliar("Combo Dúo Fiesta", 15000.0, listOf("Masa", "Jamón", "Bebida"))

    reflector.inspeccionarClase(pizzaDemoPromo)
    reflector.inspeccionarClase(comboDemo)

    // Registro de Cliente
    print("Ingrese su nombre: ")
    val nombre = readlnOrNull()?.ifBlank { "Cliente Anónimo" } ?: "Cliente Anónimo"

    print("Ingrese su dirección: ")
    val direccion = readlnOrNull()?.ifBlank { "Sin Dirección" } ?: "Sin Dirección"

    val telefono = Validacion.leerYFormatearTelefono()
    val email = Validacion.leerEmail()

    val cliente = Cliente(nombre, telefono, direccion, email)

    //Desestructuracion de Cliente
    val (nombreDesestructurado, emailDesestructurado, telefonoDesestructurado) = cliente
    println("\nCliente registrado con éxito (Desestructuración demostrada):")
    println("   • Nombre: $nombreDesestructurado | Email: $emailDesestructurado | Teléfono: $telefonoDesestructurado")

    // Catalogo de productos
    val catalogo = listOf(
        Producto("Pizza Pepperoni Tradicional", 8500.0, listOf("Masa", "Salsa de Tomate", "Queso Mozzarella", "Pepperoni")),
        Producto("Pizza Jamón Clásica", 8000.0, listOf("Masa", "Salsa de Tomate", "Queso Mozzarella", "Jamón")),
        PizzaPromocional("Pizza Promo Suprema", 9500.0, listOf("Masa", "Salsa de Tomate", "Queso Mozzarella", "Pepperoni", "Champiñones")),
        ComboFamiliar("Mega Combo Familiar", 14000.0, listOf("Masa", "Salsa de Tomate", "Queso Mozzarella", "Jamón", "Pepperoni"))
    )

    println("\n--- Menú de Productos Disponibles ---")
    catalogo.forEachIndexed { index, prod ->
        println("${index + 1}. ${prod.detallarProductos()}")
    }

    // Validacion de cantidad con Ranges
    val opcionProd = Validacion.leerCantidad("Seleccione el producto (1-${catalogo.size}): ", 1..catalogo.size)
    val productoSeleccionado = catalogo[opcionProd - 1]

    val cantidad = Validacion.leerCantidad("¿Cuántas unidades de '${productoSeleccionado.nombre}' desea (1-100)?: ", 1..100)

    //Evaluacion del rango de fechas de la promocion
    if (productoSeleccionado is PizzaPromocional && productoSeleccionado.estaEnPromocion()) {
        println("¡PROMOCIÓN ACTIVA! La fecha actual se encuentra dentro del rango promocional (${productoSeleccionado.fechaInicio} a ${productoSeleccionado.fechaFin}).")
    }

    print("¿Desea agregar notas especiales para la cocina? (Enter para omitir): ")
    val notasInput = readlnOrNull()?.takeIf { it.isNotBlank() } ?: "Sin notas"

    if (!servicio.verificarYActualizarInventario(productoSeleccionado, cantidad)) {
        println("\nNo fue posible procesar su pedido por falta de inventario.")
        return
    }


    // Creacion y resumen del pedido

    val repartidorAsignado = servicio.obtenerRepartidorDisponible()
    val productosPedido = List(cantidad) { productoSeleccionado }

    val pedidoActual = Pedido(
        idPedido = 101,
        cliente = cliente,
        productos = productosPedido,
        estado = "En preparación",
        notas = notasInput,
        repartidor = repartidorAsignado
    )

    // Desestructuracion de Pedido
    val (clienteDelPedido, productosDelPedido, totalCalculado) = pedidoActual
    println("""
        ==================================================
                      RESUMEN DEL PEDIDO #${pedidoActual.idPedido}
        ==================================================
        DATOS DEL CLIENTE:
        - Nombre:    ${clienteDelPedido.nombre}
        - Contacto:  ${clienteDelPedido.contacto}
        - Correo:    ${clienteDelPedido.email}
        - Dirección: ${clienteDelPedido.direccion}

        DETALLE DEL PRODUCTO:
        - Producto:        ${productoSeleccionado.nombre}
        - Categoría:       ${productoSeleccionado.categoria}
        - Precio Unitario: $${productoSeleccionado.precio}
        - Cantidad:        ${productosDelPedido.size}

        DESGLOSE ECONÓMICO:
        - Subtotal:        $${pedidoActual.calcularSubtotal()}
        - Total con IVA y Descuento: $totalCalculado

        INFORMACIÓN DE ENTREGA:
        - Estado:          ${pedidoActual.estado}
        - Notas del Chef:  ${pedidoActual.notas}
        - Repartidor:      ${pedidoActual.repartidor?.nombre ?: "Sin repartidor asignado"}
        ==================================================
        """.trimIndent())

    repartidorAsignado?.entregarPedido(pedidoActual.idPedido)
    pedidoActual.cambiarEstado("Entregado")

    //Operator Overloading Simulaciones para probar funciones , para cumplir la rubrica

    println("\n--- [SOBRECARGA DEL OPERADOR + (FUSIÓN DE PEDIDOS)]  ---")
    val pedidoAdicional = Pedido(
        idPedido = 102,
        cliente = cliente,
        productos = listOf(catalogo[1]), // Una pizza de jamón adicional
        estado = "En preparación",
        notas = "Agregar servilletas extra"
    )

    println("Pedido A (#${pedidoActual.idPedido}): ${pedidoActual.productos.size} productos | Total: $${pedidoActual.calcularTotal()}")
    println("Pedido B (#${pedidoAdicional.idPedido}): ${pedidoAdicional.productos.size} producto  | Total: $${pedidoAdicional.calcularTotal()}")
    val pedidoCombinado = pedidoActual + pedidoAdicional

    println("   Pedido Combinado (Pedido A + Pedido B):")
    println("   • Nuevo ID: #${pedidoCombinado.idPedido}")
    println("   • Total de productos combinados: ${pedidoCombinado.productos.size}")
    println("   • Total recalcula con descuentos e IVA: $${pedidoCombinado.calcularTotal()}")
    println("   • Notas combinadas: ${pedidoCombinado.notas}")

    //Control de Duplicados con Equals

    println("\n--- [EVALUACIÓN DE IGUALDAD Y CONTROL DE DUPLICADOS CON SET]  ---")
    val registroClientes = mutableSetOf<Cliente>()
    registroClientes.add(cliente)

    // Intentamos agregar un cliente con mismo nombre y mismo correo pero en distinta instancia
    val clienteClon = Cliente(nombre.uppercase(), "+56 (999) 000-0000", "Otra Dirección", email.lowercase())
    val fueAgregado = registroClientes.add(clienteClon)

    println("1. Intentando registrar cliente duplicado ('${clienteClon.nombre}' con correo '${clienteClon.email}')...")
    if (!fueAgregado) {
        println("   [SET ACTIVADO] El cliente ya existe en el sistema gracias a equals() y hashCode(). No se duplicó.")
    }

    // Igualdad de productos con operador ==
    val p1 = Producto("Pizza Pepperoni", 8500.0, listOf("Masa", "Queso"), "Pizza")
    val p2 = Producto("pizza pepperoni", 9900.0, listOf("Masa diferente"), "Pizza")
    println("2. Comparando dos productos con operador == (mismo nombre y categoría):")
    println("   • ¿'${p1.nombre}' == '${p2.nombre}'? -> ${p1 == p2} (Basado en equals sobrescrito)")

    //  Reporte General
    println("\n================ REPORTE DEL DÍA ================")
    val pedidosDelDia = listOf(
        pedidoActual,
        pedidoAdicional,
        pedidoCombinado
    )

    for (p in pedidosDelDia) {
        println("Pedido #${p.idPedido} | Cliente: ${p.cliente.nombre} | Pizzas: ${p.productos.size} | Total: $${p.calcularTotal()} | Estado: ${p.estado}")
    }
    println("==================================================")

}