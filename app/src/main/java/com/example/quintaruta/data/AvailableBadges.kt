package com.example.quintaruta.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WorkspacePremium
import com.example.quintaruta.data.model.Badge

// TODO: LAS BADGES SON SÍMBOLOS DE LOS POIs, HAY QUE CAMBIAR ESTO
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