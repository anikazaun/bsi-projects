package com.example.happyplaces.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.happyplaces.data.Place

@Composable
fun PlaceDetailView(
    place: Place,
    onClose: () -> Unit,
    onEdit: (Place) -> Unit // ✅ NEU
) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(place.description, style = MaterialTheme.typography.bodyLarge)

                // NEU: Note anzeigen, wenn vorhanden
                place.notes?.takeIf { it.isNotBlank() }?.let { noteText ->
                    Spacer(Modifier.height(8.dp))
                    Text("Notiz:", style = MaterialTheme.typography.titleMedium)
                    Text(noteText, style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onEdit(place) }, // ✅ Bearbeiten-Callback
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Bearbeiten")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Bearbeiten")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = onClose) {
                        Text("Schließen")
                    }
                }
            }
        }
    }
}
