package com.example.happyplaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.happyplaces.ui.AddPlaceActivity
import com.example.happyplaces.ui.SelectLocationActivity
import com.example.happyplaces.ui.theme.HappyPlacesTheme
import com.example.happyplaces.data.PlaceRepository
import com.example.happyplaces.ui.PlaceCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyPlacesTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

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
                .weight(1f) // Liste füllt den verfügbaren Platz aus
                .fillMaxWidth()
        ) {
            items(PlaceRepository.placeList) { place ->
                PlaceCard(place = place, onClick = {
                    // TODO: Detailansicht anzeigen
                })
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
}
