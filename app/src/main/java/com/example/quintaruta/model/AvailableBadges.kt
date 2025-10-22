package com.example.quintaruta.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WorkspacePremium

// Este objeto contiene una lista de todas las insignias que se pueden ganar en la app
object AvailableBadges {
    val all = listOf(
        Badge(
            id = "explorer_1",
            name = "Explorador Novato",
            description = "Visita tu primer punto de interés.",
            icon = Icons.Default.Star,
            requiredPois = 1
        ),
        Badge(
            id = "explorer_5",
            name = "Viajero Entusiasta",
            description = "Visita 5 puntos de interés.",
            icon = Icons.Default.ThumbUp,
            requiredPois = 5
        ),
        Badge(
            id = "explorer_10",
            name = "Maestro Aventurero",
            description = "Visita 10 puntos de interés.",
            icon = Icons.Default.WorkspacePremium,
            requiredPois = 10
        )
    )
}
