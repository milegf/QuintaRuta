package com.example.quintaruta.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val requiredPois: Int
)
