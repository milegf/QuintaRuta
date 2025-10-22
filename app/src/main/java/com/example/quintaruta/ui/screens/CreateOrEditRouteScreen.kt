package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
<<<<<<< HEAD
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
=======
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quintaruta.viewmodel.CreateOrEditRouteViewModel
>>>>>>> origin/2.3-2.4-terminado

@Composable
fun CreateOrEditRouteScreen(
    routeId: Long?,
    onSaved: (Long) -> Unit,
<<<<<<< HEAD
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
=======
    onCancel: () -> Unit,
    viewModel: CreateOrEditRouteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Carga la ruta si se provee un routeId, si no, prepara la creación
    LaunchedEffect(routeId) {
        viewModel.loadRoute(routeId ?: 0L)
    }

    // Muestra un indicador de carga solo cuando se edita una ruta existente
    if (!uiState.isRouteLoaded && routeId != null && routeId != 0L) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (routeId == null || routeId == 0L) "Crear nueva ruta" else "Editar ruta",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("Nombre de la ruta") },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isRouteLoaded
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                enabled = uiState.isRouteLoaded
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
                    onClick = { viewModel.saveRoute(onSaved) },
                    enabled = uiState.name.isNotBlank() && !uiState.isSaving && uiState.isRouteLoaded,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (uiState.isSaving) "Guardando..." else "Guardar")
                }
            }
        }
    }
}
>>>>>>> origin/2.3-2.4-terminado
