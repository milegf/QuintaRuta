package com.example.quintaruta.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quintaruta.model.PointOfInterest
import com.example.quintaruta.viewmodel.RouteDetailViewModel

@Composable
fun RouteDetailScreen(
    routeId: Long,
    onReturnToHome: () -> Unit,
    viewModel: RouteDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(routeId) {
        viewModel.loadPoisForRoute(routeId)
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text(
                text = "Puntos de Interés",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.pois) { poi ->
                    val isVisited = poi.id in uiState.visitedPoiIds
                    PoiCard(
                        poi = poi,
                        isVisited = isVisited,
                        // Llamamos a la nueva función del ViewModel
                        onToggleVisit = { viewModel.togglePoiVisitedState(poi.id) }
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onReturnToHome,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Menú Principal")
            }
        }
    }
}

@Composable
fun PoiCard(
    poi: PointOfInterest,
    isVisited: Boolean,
    onToggleVisit: () -> Unit // Cambiamos el nombre del parámetro para más claridad
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = poi.image),
                    contentDescription = poi.name,
                    modifier = Modifier.size(80.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = poi.name, style = MaterialTheme.typography.titleLarge)
                    Text(text = poi.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onToggleVisit,
                // El botón ahora siempre está habilitado
                enabled = true, 
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    // Cambiamos el color para que se distinga si está visitado o no
                    containerColor = if (isVisited) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                )
            ) {
                // Cambiamos el texto del botón según el estado
                Text(if (isVisited) "Marcar como Pendiente" else "Check-in")
            }
        }
    }
}
