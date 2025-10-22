package com.example.quintaruta.data.model

data class Trivia(
    val id: Long = 0L,
    val poiId: Long,
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: String,
    val respondida: Boolean = false
)