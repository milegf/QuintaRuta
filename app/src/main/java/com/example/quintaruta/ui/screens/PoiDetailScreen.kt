package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quintaruta.viewmodel.PoiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoiDetailScreen(
    routeId: Long,
    poiId: Long,
    onBack: () -> Unit,
    onOpenMap: () -> Unit,
    onOpenTrivia: () -> Unit,
    viewModel: PoiViewModel = viewModel()
) {
    val pois by viewModel.pois.collectAsState()
    val poiSeleccionado = pois.find { it.id == poiId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(poiSeleccionado?.nombre ?: "Detalle del POI") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        poiSeleccionado?.let { poi ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(poi.descripcion, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onOpenMap, modifier = Modifier.fillMaxWidth()) {
                    Text("Ver en mapa")
                }
                Spacer(Modifier.height(8.dp))
                Button(onClick = onOpenTrivia, modifier = Modifier.fillMaxWidth()) {
                    Text("Ir a trivia")
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("POI no encontrado")
        }
    }
}
