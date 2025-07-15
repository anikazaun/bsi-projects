package com.example.happyplaces.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.happyplaces.data.Place
import com.example.happyplaces.data.PlaceRepository

@Composable
fun EditPlaceScreen(placeToEdit: Place, onPlaceUpdated: () -> Unit) {
    val context = LocalContext.current

    var title by remember { mutableStateOf(placeToEdit.title) }
    var description by remember { mutableStateOf(placeToEdit.description) }
    var note by remember { mutableStateOf(placeToEdit.notes ?: "") }
    var selectedImageUri by remember { mutableStateOf(placeToEdit.imageUri?.let { Uri.parse(it) }) }

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

        // Bildauswahl
        Button(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
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

        // Speichern
        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    val updatedPlace = placeToEdit.copy(
                        title = title,
                        description = description,
                        notes = note.takeIf { it.isNotBlank() },
                        imageUri = selectedImageUri?.toString()
                    )
                    PlaceRepository.updatePlace(updatedPlace)

                    Toast.makeText(context, "Ort aktualisiert!", Toast.LENGTH_SHORT).show()
                    onPlaceUpdated()
                } else {
                    Toast.makeText(context, "Bitte Titel und Beschreibung angeben", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Änderungen speichern")
        }
    }
}
