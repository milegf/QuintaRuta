package com.example.quintaruta.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quintaruta.data.model.Trivia
import com.example.quintaruta.viewmodel.TriviaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaScreen(
    poiId: Long,
    viewModel: TriviaViewModel,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val trivias by viewModel.trivias.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()

    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(poiId) {
        viewModel.loadTriviasByPoi(poiId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Trivia del lugar", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                trivias.isEmpty() -> {
                    Text("No hay trivias disponibles para este lugar.")
                }
                else -> {
                    TriviaList(
                        trivias = trivias,
                        onRespuestaSeleccionada = { trivia, respuesta ->
                            if (respuesta == trivia.respuestaCorrecta) {
                                viewModel.marcarTriviaRespondida(trivia)
                            } else {
                                Toast.makeText(context, "Respuesta incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TriviaList(
    trivias: List<Trivia>,
    onRespuestaSeleccionada: (Trivia, String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        trivias.forEach { trivia ->
            if (!trivia.respondida) {
                TriviaCard(trivia, onRespuestaSeleccionada)
            } else {
                TriviaCardRespondida(trivia)
            }
        }
    }
}

@Composable
fun TriviaCard(
    trivia: Trivia,
    onRespuestaSeleccionada: (Trivia, String) -> Unit
) {
    var seleccion by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = trivia.pregunta,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            trivia.opciones.forEach { opcion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (opcion == seleccion),
                            onClick = { seleccion = opcion }
                        )
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (opcion == seleccion),
                        onClick = { seleccion = opcion }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = opcion)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onRespuestaSeleccionada(trivia, seleccion) },
                enabled = seleccion.isNotEmpty(),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Responder")
            }
        }
    }
}

@Composable
fun TriviaCardRespondida(trivia: Trivia) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = trivia.pregunta,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "✔ Trivia completada",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
