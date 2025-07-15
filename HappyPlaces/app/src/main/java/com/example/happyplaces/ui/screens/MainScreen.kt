package com.example.happyplaces.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.happyplaces.data.Place
import com.example.happyplaces.data.PlaceRepository
import com.example.happyplaces.ui.activities.AddPlaceActivity
import com.example.happyplaces.ui.activities.SelectLocationActivity
import com.example.happyplaces.ui.composables.PlaceCard
import com.example.happyplaces.ui.composables.PlaceDetailView

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val lat = data?.getDoubleExtra("latitude", 0.0) ?: 0.0
            val lon = data?.getDoubleExtra("longitude", 0.0) ?: 0.0

            // Starte AddPlaceActivity mit ausgewählten Koordinaten
            val intent = Intent(context, AddPlaceActivity::class.java).apply {
                putExtra("latitude", lat)
                putExtra("longitude", lon)
            }
            context.startActivity(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Meine Orte", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(PlaceRepository.placeList) { place ->
                PlaceCard(
                    place = place,
                    onClick = {
                        selectedPlace = place
                    },
                    onDelete = {
                        PlaceRepository.deletePlace(place)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, SelectLocationActivity::class.java)
                launcher.launch(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Neuen Ort hinzufügen")
        }
    }

    // Detailansicht anzeigen, wenn ein Ort ausgewählt ist
    selectedPlace?.let { place ->
        PlaceDetailView(
            place = place,
            onClose = { selectedPlace = null },
            onEdit = { toEdit ->
                selectedPlace = null
                val intent = Intent(context, com.example.happyplaces.ui.activities.EditPlaceActivity::class.java)
                intent.putExtra("place_id", toEdit.id)
                context.startActivity(intent)
            }
        )
    }
}
