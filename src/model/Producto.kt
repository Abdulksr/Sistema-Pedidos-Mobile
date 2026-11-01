package model

class Producto(
    val nombre: String,
    val precio: Double,
    val ingredientes: List<String>
) {

    fun detallarProductos(): String {
        return "$nombre - Precio: $$precio | Ingredientes: ${ingredientes.joinToString(", ")}"
    }
}