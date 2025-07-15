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
import com.example.happyplaces.ui.composables.PlaceDetailView

@Composable
fun AddPlaceScreen(startLatitude: Double, startLongitude: Double) {
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf(startLatitude) }
    var longitude by remember { mutableStateOf(startLongitude) }

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Eingabefelder zum Ort hinzufügen
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Beschreibung") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            imagePicker.launch("image/*")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Bild auswählen")
        }

        Spacer(modifier = Modifier.height(8.dp))

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

        Button(onClick = {
            if (title.isNotBlank() && description.isNotBlank()) {
                PlaceRepository.addPlace(
                    Place(
                        title = title,
                        description = description,
                        imageUri = selectedImageUri?.toString(),
                        latitude = latitude,
                        longitude = longitude
                    )
                )
                // Felder zurücksetzen
                title = ""
                description = ""
                selectedImageUri = null

                Toast.makeText(context, "Ort gespeichert!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Bitte Titel und Beschreibung angeben", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Ort speichern")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Liste aller gespeicherten Orte
        Text("Gespeicherte Orte:", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(PlaceRepository.placeList) { place ->
                PlaceCard(
                    place = place,
                    onClick = { selectedPlace = place },
                    onDelete = { PlaceRepository.deletePlace(place) }
                )
            }
        }
    }

    // Detailansicht, wenn ein Ort ausgewählt ist
    if (selectedPlace != null) {
        PlaceDetailView(
            place = selectedPlace!!,
            onClose = { selectedPlace = null }
        )
    }
}