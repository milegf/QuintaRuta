// File: ExploreScreen.kt
package com.example.quintaruta.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExploreScreen(
    onRouteClick: (Long) -> Unit,
    onCreateRoute: () -> Unit
) {
    // Rutas base de la V Región
    val routes = listOf(
        Pair(1L, "Ruta de Miradores"),
        Pair(2L, "Circuito Costero")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Explorar rutas de la V Región")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(routes) { (id, name) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRouteClick(id) }
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = name,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Button(
            onClick = onCreateRoute,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear nueva ruta")
        }
    }
}
