package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateOrEditRouteScreen(
    routeId: Long?,
    onSaved: (Long) -> Unit,
    onCancel: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(routeId) {
        if (routeId != null) {
            // TODO: Reemplazar por carga real desde base de datos
            name = "Ruta #$routeId"
            description = "Descripción de ejemplo"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (routeId == null) "Crear ruta" else "Editar ruta",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)
        )

        Spacer(Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    // TODO: Guardar en BD y obtener id real
                    val newId = routeId ?: System.currentTimeMillis()
                    onSaved(newId)
                },
                enabled = name.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (routeId == null) "Crear" else "Guardar")
            }
        }
    }
}