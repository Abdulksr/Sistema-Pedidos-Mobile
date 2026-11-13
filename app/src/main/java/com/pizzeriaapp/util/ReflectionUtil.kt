package com.pizzeriaapp.util

import com.pizzeriaapp.annotation.Promocionable

class ReflectionUtil {

    fun inspeccionarClase(objeto: Any){
        val clazz = objeto.javaClass
        println("\n[REFLECTION] Inspección dinámica de la clase: ${clazz.simpleName}")

        //1 Detectar si la clase tiene la anotacion promocionable
        val promo = clazz.getAnnotation(Promocionable::class.java)
        if(promo != null){
            println("¡ANOTACIÓN DETECTADA! -> @Promocionable: ${promo.descripcion}")
        } else {
            println("Sin anotaciones promocionales.")
        }

        //2 Listar todos los atributos / propiedades
        println("Propiedades declaradas:")
        clazz.declaredFields.forEach { campo ->
            println("      • ${campo.name}: ${campo.type.simpleName}")
        }

        //3 Listar metodos
        println("    Métodos disponibles:")
        clazz.declaredMethods
            .filter { !it.name.contains("$") && !it.name.contains("component") }
            .take(6)
            .forEach { metodo ->
                println("      • ${metodo.name}()")
            }
    }
}