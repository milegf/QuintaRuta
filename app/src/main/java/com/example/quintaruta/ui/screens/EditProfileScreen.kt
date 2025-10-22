package com.example.quintaruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditProfileScreen(
    currentName: String,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(currentName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Editar Perfil")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de Usuario") },
            modifier = Modifier.fillMaxWidth()
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
                onClick = { onSave(name) },
                enabled = name.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
        }
    }
}
