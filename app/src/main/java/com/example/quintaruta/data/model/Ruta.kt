package com.example.quintaruta.data.model

data class Ruta(
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val imagenUrl: String? = null,
    val creador: String
)
