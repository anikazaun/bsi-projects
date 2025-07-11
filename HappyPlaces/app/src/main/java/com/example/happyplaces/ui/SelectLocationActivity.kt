package com.example.happyplaces.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SelectLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedLat by remember { mutableStateOf(52.5200) }  // Start z.B. Berlin
            var selectedLon by remember { mutableStateOf(13.4050) }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                MapComponent(
                    modifier = Modifier.weight(1f),
                    onLocationSelected = { lat, lon ->
                        selectedLat = lat
                        selectedLon = lon
                    }
                )

                Button(
                    onClick = {
                        val intent = Intent()
                        intent.putExtra("latitude", selectedLat)
                        intent.putExtra("longitude", selectedLon)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Ort bestätigen")
                }
            }
        }
    }
}
