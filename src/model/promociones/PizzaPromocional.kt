package model.promociones

import annotation.Promocionable
import model.Producto
import java.time.LocalDate

@Promocionable("Pizza promocional con 15% de descuento")
class PizzaPromocional(
    nombre: String,
    precio: Double,
    ingredientes: List<String>,
    val fechaInicio: LocalDate = LocalDate.now().minusDays(2),
    val fechaFin: LocalDate = LocalDate.now().plusDays(5),
    val porcentajeDescuento: Double = 0.15
) : Producto(nombre, precio, ingredientes, "Pizzas Promocional") {
    fun estaEnPromocion(fechaActual: LocalDate = LocalDate.now()): Boolean {
        return fechaActual in fechaInicio..fechaFin
    }
}