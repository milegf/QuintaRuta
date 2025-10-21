// File: TriviaScreen.kt
package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TriviaScreen(
    poiId: Long,
    onFinish: () -> Unit
) {

    var currentQuestion by remember { mutableStateOf(1) }
    val totalQuestions = 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Trivia del POI #$poiId",
            style = MaterialTheme.typography.headlineSmall
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Pregunta $currentQuestion de $totalQuestions")
        Text("Aquí irá el enunciado de la pregunta.")
        Text("Opciones y lógica por implementar más adelante.")

        Spacer(modifier = Modifier.weight(1f))

        if (currentQuestion < totalQuestions) {
            Button(
                onClick = { currentQuestion++ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente pregunta")
            }
        } else {
            Button(
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar trivia")
            }
        }
    }
}
