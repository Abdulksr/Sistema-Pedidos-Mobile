package model

class Cliente(
    nombre: String,
    contacto: String,
    val direccion: String,
    val email: String
) : Usuario(nombre, contacto) {

    fun imprimirCliente() {
        println("=== Datos del Cliente ===")
        println("Nombre: $nombre")
        println("Contacto: $contacto")
        println("Email: $email")
        println("Dirección: $direccion")
        println("=========================")
    }
}