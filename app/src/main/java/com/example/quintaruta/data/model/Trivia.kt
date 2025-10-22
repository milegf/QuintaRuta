package com.example.quintaruta.data.model

data class Trivia(
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)