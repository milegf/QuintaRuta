package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PoiDetailScreen(
    routeId: Long,
    poiId: Long,
    onOpenMap: () -> Unit,
    onStartTrivia: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Punto de Interés #$poiId (Ruta #$routeId)",
            style = MaterialTheme.typography.headlineSmall
        )

        Divider()

        Text("Nombre del POI: (por completar)")
        Text("Descripción del lugar: (por completar)")
        Text("Datos adicionales o multimedia: (por completar)")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onOpenMap, modifier = Modifier.fillMaxWidth()) {
            Text("Ver en mapa")
        }

        Button(onClick = onStartTrivia, modifier = Modifier.fillMaxWidth()) {
            Text("Iniciar trivia")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}
