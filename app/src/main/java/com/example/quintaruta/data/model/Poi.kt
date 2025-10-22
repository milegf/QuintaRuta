package com.example.quintaruta.data.model

data class Poi(
    val id: Long = 0,
    val rutaId: Long,
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double,
    val imagenUrl: String? = null
)
