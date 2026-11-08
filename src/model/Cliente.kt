package model

class Cliente(
    nombre: String,
    contacto: String,
    val direccion: String,
    val email: String
) : Usuario(nombre, contacto) {


    //Desestructuracionoperator
    operator fun component1() = nombre
    operator fun component2() = email
    operator fun component3() = contacto

    //Metodos equals y hashcode
    override fun equals(other: Any?): Boolean{
        if(this === other) return true
        if(other !is Cliente) return false
        return this.nombre.equals(other.nombre , ignoreCase = true) &&
                this.email.equals(other.email, ignoreCase=true)
    }

    override fun hashCode(): Int{
        var result = nombre.lowercase().hashCode()
        result = 31 * result + email.lowercase().hashCode()
        return result
    }

    fun imprimirCliente() {
        println("=== Datos del Cliente ===")
        println("Nombre: $nombre | Contacto: $contacto | Email: $email | Dirección: $direccion")
        println("=========================")
    }
}