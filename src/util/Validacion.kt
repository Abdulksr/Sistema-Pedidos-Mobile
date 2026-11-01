package util

object Validacion {

    // 1. Validación de cantidad de pizzas
    fun leerCantidad(mensaje: String = "¿Cuantas pizzas desea?: "): Int {
        while (true) {
            print(mensaje)

            val cantidad = readlnOrNull()?.toIntOrNull()

            if (cantidad != null && cantidad > 0) {
                return cantidad
            }
            println("Error: Ingrese una cantidad válida mayor que cero.")
        }
    }

    // 2. Validación de teléfono
    fun validarTelefono(telefono: String?): String {
        val telefonoLimpio = telefono?.trim()
        val regexTelefono = Regex("""^\+?[0-9]{8,}$""")
        return if (!telefonoLimpio.isNullOrBlank() && regexTelefono.matches(telefonoLimpio)) {
            telefonoLimpio
        } else {
            println("Teléfono inválido. Se asignará el valor predeterminado: (000) 000-0000")
            "(000) 000-0000"
        }
    }

    // 3. Validación de correo:
    fun validarEmail(email: String?): String {
        val emailLimpio = email?.trim()
        val regexEmail = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}${'$'}""")

        return if (!emailLimpio.isNullOrBlank() && regexEmail.matches(emailLimpio)) {
            emailLimpio
        } else {
            println("Correo inválido. Se asignará el valor predeterminado: correo@invalido.com")
            "correo@invalido.com"
        }
    }


}