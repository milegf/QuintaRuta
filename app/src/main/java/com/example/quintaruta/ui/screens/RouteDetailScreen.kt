package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RouteDetailScreen(
    routeId: Long,
    onPoiClick: (Long) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Detalle de la Ruta #$routeId",
            style = MaterialTheme.typography.headlineSmall
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Información de la ruta (nombre, descripción, etc.)")
        Text("Aquí se mostrarán los puntos de interés asociados.")

        Button(onClick = { onPoiClick(1L) }) {
            Text("Ver POI #1")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}
