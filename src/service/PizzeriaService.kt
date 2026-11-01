package service

import model.Producto
import model.Repartidor

class PizzeriaService {

    val inventario = mutableMapOf(
        "Masa" to 10,
        "Salsa de Tomate" to 10,
        "Queso Mozzarella" to 10,
        "Pepperoni" to 8,
        "Jamón" to 6,
        "Champiñones" to 5
    )

    val repartidores = listOf(
        Repartidor("Carlos Silva", "+56911112222", disponible = true),
        Repartidor("Pedro Morales", "+56933334444", disponible = false),
        Repartidor("Lucía Gómez", "+56955556666", disponible = true)
    )

    // Buscar el primer repartidor disponible
    fun obtenerRepartidorDisponible(): Repartidor? {
        return repartidores.find { it.disponible }
    }

    // Verificación de stock con 'when' y actualización de inventario
    fun verificarYActualizarInventario(producto: Producto, cantidad: Int): Boolean {
        val faltantes = producto.ingredientes.filter { (inventario[it] ?: 0) < cantidad }

        return when {

            faltantes.isEmpty() -> {
                for (ingrediente in producto.ingredientes) {
                    inventario[ingrediente] = (inventario[ingrediente] ?: 0) - cantidad
                }
                println("Stock verificado. Inventario descontado con éxito.")
                true
            }

            else -> {
                println("❌ Pedido rechazado: Stock insuficiente.")
                println("Ingredientes que faltan: ${faltantes.joinToString(", ")}")
                println(
                    "Pasos para reabastecer: Generar orden de compra al proveedor para reponer: ${
                        faltantes.joinToString(
                            ", "
                        )
                    }."
                )
                false
            }
        }
    }

    //Calculo del total
    fun calcularTotales(subtotal: Double, cantidadPizzas: Int): Map<String, Double> {
        val porcentajeDescuento = if (cantidadPizzas > 3) 0.10 else 0.0
        val montoDescuento = subtotal * porcentajeDescuento
        val baseConDescuento = subtotal - montoDescuento
        val impuestos = baseConDescuento * 0.19
        val totalFinal = baseConDescuento + impuestos

        return mapOf(
            "subtotal" to subtotal,
            "descuento" to montoDescuento,
            "impuestos" to impuestos,
            "totalFinal" to totalFinal
        )
    }
}