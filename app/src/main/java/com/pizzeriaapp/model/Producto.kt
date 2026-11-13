package com.pizzeriaapp.model

open class Producto(
    val nombre: String,
    val precio: Double,
    val ingredientes: List<String>,
    val categoria: String = "Pizza"
) {
    //Metodos equals y hash
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is Producto) return false

        return this.nombre.equals(other.nombre, ignoreCase = true) &&
                this.categoria.equals(other.categoria, ignoreCase = true)
    }

    override fun hashCode(): Int {
        var result = nombre.lowercase().hashCode()
        result = 31 * result + categoria.lowercase().hashCode()
        return result
    }

    fun detallarProductos(): String {
        return "[$categoria] $nombre - Precio: $$precio | Ingredientes: ${ingredientes.joinToString(", ")}"
    }
}