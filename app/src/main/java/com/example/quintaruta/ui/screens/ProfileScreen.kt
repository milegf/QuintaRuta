package com.example.quintaruta.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.quintaruta.model.Badge
import com.example.quintaruta.viewmodel.ProfileUiState

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEditRoute: (Long) -> Unit,
    onDeleteRoute: (Long) -> Unit,
    onEditProfileClick: () -> Unit
) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Sección de Información de Usuario ---
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = uiState.profileImageUrl ?: "https://via.placeholder.com/120"),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(80.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = uiState.userName, style = MaterialTheme.typography.titleLarge)
                        Text(text = uiState.userEmail, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onEditProfileClick, modifier = Modifier.fillMaxWidth()) {
                    Text("Editar Perfil")
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
            }

            // --- Sección de Progreso e Insignias ---
            item {
                Text(text = "Mi Progreso", style = MaterialTheme.typography.titleMedium)
                Text("Puntos de Interés visitados: ${uiState.visitedPoisCount}")

                if (uiState.unlockedBadges.isNotEmpty()) {
                    Text("Insignias Ganadas:", modifier = Modifier.padding(top = 8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(uiState.unlockedBadges) { badge ->
                            BadgeView(badge = badge)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
            }

            // --- Sección de Mis Rutas ---
            item {
                Text(text = "Mis Rutas", style = MaterialTheme.typography.titleMedium)
            }
            if (uiState.userRoutes.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxHeight(), contentAlignment = Alignment.Center) {
                        Text("No tienes rutas creadas aún.")
                    }
                }
            } else {
                items(uiState.userRoutes) { (id, name) ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = name, modifier = Modifier.weight(1f))
                            IconButton(onClick = { onEditRoute(id) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar Ruta")
                            }
                            IconButton(onClick = { onDeleteRoute(id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar Ruta")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeView(badge: Badge) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)) {
        Icon(imageVector = badge.icon, contentDescription = badge.name, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
        Text(text = badge.name, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}
