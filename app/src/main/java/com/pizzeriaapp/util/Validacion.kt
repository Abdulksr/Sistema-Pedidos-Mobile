package com.pizzeriaapp.util

import java.time.LocalDate

object Validacion {

    // 1. Validación de cantidad de pizzas con Rango
    fun leerCantidad(mensaje: String = "¿Cuantas unidades desea (1-100)?: ", rango: IntRange = 1..100): Int {
        while (true) {
            print(mensaje)
            val entrada = readlnOrNull()
            try {
                val cantidad = entrada?.toInt() ?: throw NumberFormatException("Entrada vacia")
                if (cantidad in rango){
                    return cantidad
                }else{
                    println("Error: La cantidad debe estar entre ${rango.first} y ${rango.last}.")
                }
            }catch (e: NumberFormatException){
                println("Error: Ingrese un número entero válido.")
            }
        }
    }

    // 2. Validación de teléfono Regex
    fun leerYFormatearTelefono(): String {
        while (true) {
            print("Ingrese su teléfono (ej: 56912345678): ")
            val entrada = readlnOrNull()?.trim() ?: ""
            val digitos = entrada.filter { it.isDigit() }

            if (digitos.length >= 11) {
                val codigoPais = digitos.substring(0, 2)
                val area = digitos.substring(2, 5)
                val parte1 = digitos.substring(5, 8)
                val parte2 = digitos.substring(8, 12.coerceAtMost(digitos.length))
                return "+$codigoPais ($area) $parte1-$parte2"
            }else{
                    println("Error: Debe ingresar un teléfono válido con al menos 11 dígitos (código país + número).")
            }
        }
    }

    // 3. Validación de correo Regex
    fun leerEmail(): String {
        val regexEmail = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""")
        while (true) {
            print("Ingrese su correo electrónico: ")
            val entrada = readlnOrNull()?.trim() ?: ""
            if (regexEmail.matches(entrada)) {
                return entrada
            }
            println("Error: El formato debe ser estándar (ejemplo: usuario@dominio.com). Intente nuevamente.")
        }
    }

}