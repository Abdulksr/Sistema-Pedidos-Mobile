package model.promociones

import annotation.Promocionable
import model.Producto
import java.time.LocalDate

@Promocionable("Mega Combo con 20% de descuento")
class ComboFamiliar(
    nombre: String,
    precio: Double,
    ingredientes: List<String>,
    val fechaInicio: LocalDate = LocalDate.now().minusDays(2),
    val fechaFin: LocalDate = LocalDate.now().plusDays(5),
    val porcentajeDescuento: Double = 0.20
) : Producto(nombre, precio, ingredientes, "Combo"){
    fun estaEnPromocion(fechaActual: LocalDate = LocalDate.now()): Boolean {
        return fechaActual in fechaInicio..fechaFin
    }
}