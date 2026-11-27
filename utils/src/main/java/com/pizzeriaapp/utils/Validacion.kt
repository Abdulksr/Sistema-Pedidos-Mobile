package com.pizzeriaapp.utils

object Validacion {
    private val regexEmail = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""")

    fun esCantidadValida(cantidad: Int, rango: IntRange = 1..100): Boolean {
        return cantidad in rango
    }

    fun esEmailValido(email: String): Boolean {
        return regexEmail.matches(email.trim())
    }

    fun formatearTelefonoSiValido(telefono: String): String? {
        val digitos = telefono.filter { it.isDigit() }
        if (digitos.length < 11) return null
        val codigoPais = digitos.substring(0, 2)
        val area = digitos.substring(2, 5)
        val parte1 = digitos.substring(5, 8)
        val parte2 = digitos.substring(8, 12.coerceAtMost(digitos.length))
        return "+$codigoPais ($area) $parte1-$parte2"
    }
}