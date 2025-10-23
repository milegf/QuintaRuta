package com.example.quintaruta.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.quintaruta.viewmodel.RutaViewModel
import com.example.quintaruta.viewmodel.PoiViewModel
import com.example.quintaruta.data.model.Poi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreen(
    routeId: Long,
    onBack: () -> Unit,
    onPoiClick: (Poi) -> Unit,
    rutaViewModel: RutaViewModel = viewModel(),
    poiViewModel: PoiViewModel = viewModel()
) {
    val rutas by rutaViewModel.rutas.collectAsState()
    val rutaSeleccionada = rutas.find { it.id == routeId }

    LaunchedEffect(routeId) {
        poiViewModel.cargarPoisPorRuta(routeId)
    }

    val poisState by poiViewModel.pois.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(rutaSeleccionada?.nombre ?: "Detalle de la Ruta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (rutaSeleccionada == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Ruta no encontrada", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = rutaSeleccionada.nombre,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = rutaSeleccionada.descripcion,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Categoría: ${rutaSeleccionada.categoria}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    HorizontalDivider(
                        Modifier.padding(vertical = 8.dp),
                        DividerDefaults.Thickness,
                        DividerDefaults.color
                    )
                    Text(
                        text = "Puntos de Interés",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(poisState) { poi ->
                    PoiCard(poi = poi, onPoiClick = { onPoiClick(poi) })
                }
            }
        }
    }
}

@Composable
fun PoiCard(
    poi: Poi,
    onPoiClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onPoiClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            poi.imagenUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(model = url),
                    contentDescription = poi.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(8.dp))
            }

            Text(text = poi.nombre, style = MaterialTheme.typography.titleLarge)
            Text(text = poi.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onPoiClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a trivia")
            }
        }
    }
}
