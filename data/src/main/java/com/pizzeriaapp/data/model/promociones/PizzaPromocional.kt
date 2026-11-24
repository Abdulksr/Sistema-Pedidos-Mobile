package com.pizzeriaapp.data.model.promociones

import com.pizzeriaapp.utils.annotation.Promocionable
import com.pizzeriaapp.data.model.Producto
import java.util.Date
import java.util.concurrent.TimeUnit

@Promocionable("Pizza promocional con 15% de descuento")
class PizzaPromocional(
    nombre: String,
    precio: Double,
    ingredientes: List<String>,
    val fechaInicio: Date = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)),
    val fechaFin: Date = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5)),
    val porcentajeDescuento: Double = 0.15
) : Producto(nombre, precio, ingredientes, "Pizzas Promocional") {
    fun estaEnPromocion(fechaActual: Date = Date()): Boolean {
        return fechaActual in fechaInicio..fechaFin
    }
}