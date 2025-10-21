package com.example.quintaruta.data

import com.example.quintaruta.R
import com.example.quintaruta.model.PointOfInterest

// Datos de prueba para simular la base de datos
object MockData {
    val pois = listOf(
        PointOfInterest(
            id = 1,
            routeId = 1, // Pertenece a la "Ruta del Vino"
            name = "Viña Concha y Toro",
            description = "Una de las viñas más grandes y famosas de Chile.",
            image = R.drawable.ic_launcher_background, // Reemplazar con imagen real
            latitude = -33.6248, 
            longitude = -70.5825,
            trivia = null
        ),
        PointOfInterest(
            id = 2,
            routeId = 1,
            name = "Viña Santa Rita",
            description = "Historia, cultura y vino en un solo lugar.",
            image = R.drawable.ic_launcher_background, // Reemplazar con imagen real
            latitude = -33.7000, 
            longitude = -70.6833,
            trivia = null
        ),
        PointOfInterest(
            id = 3,
            routeId = 2, // Pertenece a la "Ruta de las Estrellas"
            name = "Observatorio Mamalluca",
            description = "El primer observatorio turístico de Chile.",
            image = R.drawable.ic_launcher_background, // Reemplazar con imagen real
            latitude = -30.2597, 
            longitude = -70.7333,
            trivia = null
        )
    )
}
