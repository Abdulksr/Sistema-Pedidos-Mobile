package com.pizzeriaapp.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Promocionable(val descripcion: String = "Producto con promocion especial") {

}