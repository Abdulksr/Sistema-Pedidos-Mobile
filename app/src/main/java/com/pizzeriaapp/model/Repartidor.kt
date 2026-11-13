package com.pizzeriaapp.model

class Repartidor(
    nombre: String,
    contacto: String,
    val disponible: Boolean = true
) : Usuario(nombre, contacto) {

    fun entregarPedido(idPedido: Int) {
        println("El repartidor $nombre está entregando el pedido #$idPedido.")
    }
}