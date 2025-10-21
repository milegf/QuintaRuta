package com.example.quintaruta.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    profileImageUrl: String?,
    // TODO: MODIFICAR CUANDO SE CONFIGURE LA DATA CLASS
    userRoutes: List<Pair<Long, String>>,
    onEditRoute: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = profileImageUrl ?: "https://via.placeholder.com/120"
                ),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
            )

            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text(
            text = "Mis rutas",
            style = MaterialTheme.typography.titleSmall
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            items(userRoutes) { (id, name) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEditRoute(id) }
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = name,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        if (userRoutes.isEmpty()) {
            Text("No tienes rutas creadas aún.")
        }
    }
}
