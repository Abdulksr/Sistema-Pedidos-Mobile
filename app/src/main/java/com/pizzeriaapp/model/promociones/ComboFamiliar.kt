package com.pizzeriaapp.model.promociones

import com.pizzeriaapp.annotation.Promocionable
import com.pizzeriaapp.model.Producto
import java.util.Date
import java.util.concurrent.TimeUnit

@Promocionable("Mega Combo con 20% de descuento")
class ComboFamiliar(
    nombre: String,
    precio: Double,
    ingredientes: List<String>,
    val fechaInicio: Date = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)),
    val fechaFin: Date = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5)),
    val porcentajeDescuento: Double = 0.20
) : Producto(nombre, precio, ingredientes, "Combo"){
    fun estaEnPromocion(fechaActual: Date = Date()): Boolean {
        return fechaActual in fechaInicio..fechaFin
    }
}