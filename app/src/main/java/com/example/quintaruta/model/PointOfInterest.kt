package com.example.quintaruta.model

import androidx.annotation.DrawableRes

// Representa un único Punto de Interés dentro de una ruta
data class PointOfInterest(
    val id: Long,
    val routeId: Long,
    val name: String,
    val description: String,
    @DrawableRes val image: Int, // ID del recurso drawable
    val latitude: Double,
    val longitude: Double,
    val trivia: Trivia? = null
)

// Representa una trivia asociada a un POI
data class Trivia(
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
