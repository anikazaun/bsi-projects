package com.example.happyplaces.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.happyplaces.data.Place

@Composable
fun PlaceDetailView(place: Place, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(32.dp)
            .clickable { onClose() } // Klick außerhalb schließt
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable(enabled = false) {}, // Klick in Karte nicht schließen
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(place.title, style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(8.dp))
                place.imageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Bild von ${place.title}",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(place.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                    Text("Schließen")
                }
            }
        }
    }
}