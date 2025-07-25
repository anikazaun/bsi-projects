package com.example.happyplaces.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.happyplaces.data.Place
import com.example.happyplaces.data.PlaceRepository
import com.example.happyplaces.ui.composables.PlaceCard

@Composable
fun AddPlaceScreen(startLatitude: Double, startLongitude: Double) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Titel
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Beschreibung
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Beschreibung") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Notiz
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Notiz (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bild auswählen
        Button(onClick = {
            imagePicker.launch("image/*")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Bild auswählen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bild-Vorschau
        selectedImageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Ausgewähltes Bild",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ort speichern
        Button(onClick = {
            if (title.isNotBlank() && description.isNotBlank()) {
                val place = Place(
                    title = title,
                    description = description,
                    notes = note.takeIf { it.isNotBlank() },
                    imageUri = selectedImageUri?.toString(),
                    latitude = startLatitude,
                    longitude = startLongitude
                )
                PlaceRepository.addPlace(place)

                // Felder zurücksetzen
                title = ""
                description = ""
                note = ""
                selectedImageUri = null

                Toast.makeText(context, "Ort gespeichert!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Bitte Titel und Beschreibung angeben", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Ort speichern")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Liste der Orte anzeigen
        Text("Gespeicherte Orte:", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(PlaceRepository.placeList) { place ->
                PlaceCard(
                    place = place,
                    onClick = { /* Kein Detailview hier nötig */ },
                    onDelete = { PlaceRepository.deletePlace(place) }
                )
            }
        }
    }
}
