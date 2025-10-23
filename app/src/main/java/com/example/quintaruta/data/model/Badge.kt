package com.example.quintaruta.data.model

import androidx.compose.ui.graphics.vector.ImageVector

// TODO: LAS IMAGENES DE LAS BADGES ESTARÁN EN UN LINK PUBLICO DE DRIVE (YA COMPARTIDO)
data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val requiredPois: Int
)