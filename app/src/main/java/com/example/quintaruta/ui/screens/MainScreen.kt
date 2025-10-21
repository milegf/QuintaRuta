package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onOpenExplore: () -> Unit,
    onOpenProfile: () -> Unit,
    onCreateRoute: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Bienvenido a QuintaRuta!")

        Button(onClick = onOpenExplore) { Text("Explorar") }

        Button(onClick = onOpenProfile) { Text("Perfil") }

        Button(onClick = onCreateRoute) { Text("Crear Ruta") }

        // Button(onClick = onLogout) { Text("Cerrar sesión") }

    }
}