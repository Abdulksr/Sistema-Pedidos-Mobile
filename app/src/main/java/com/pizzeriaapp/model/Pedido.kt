package com.pizzeriaapp.model

class Pedido(
    val idPedido: Int,
    val cliente: Cliente,
    val productos: List<Producto>,
    var estado: String = "En preparación",
    val notas: String? = null,
    var repartidor: Repartidor? = null
) {
    fun calcularSubtotal(): Double {
        return productos.sumOf { it.precio }
    }

    //calcula el total con el descuento de 3 o mas pizzas
    fun calcularTotal(): Double {
        val subtotal = calcularSubtotal()
        val descuento = if (productos.size > 3) subtotal * 0.10 else 0.0
        val baseConDescuento = subtotal - descuento
        val iva = baseConDescuento * 0.19 // 19% IVA
        return baseConDescuento + iva
    }

    fun cambiarEstado(nuevoEstado: String) {
        this.estado = nuevoEstado
    }

    //Desestructuracion de Pedido
    operator fun component1(): Cliente = cliente
    operator fun component2(): List<Producto> = productos
    operator fun component3(): Double = calcularTotal()

    //Sobrecarga del operador (Suma dos pedidos)
    operator fun plus(otro: Pedido): Pedido{
        val productosCombinados = this.productos + otro.productos
        val notasCombinados = listOfNotNull(this.notas,otro.notas).joinToString(" | ")
        return Pedido(
            idPedido = this.idPedido + 1000,
            cliente = this.cliente,
            productos = productosCombinados,
            estado = this.estado,
            notas = if(notasCombinados.isNotBlank()) notasCombinados else null,
            repartidor = this.repartidor?: otro.repartidor
        )
    }
}