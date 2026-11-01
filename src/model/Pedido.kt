package model

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

    fun cambiarEstado(nuevoEstado: String) {
        this.estado = nuevoEstado
    }
}