package com.example.happyplaces.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.happyplaces.ui.composables.MapComponent

class SelectLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedLat by remember { mutableStateOf(52.5200) }  // Start z.B. Berlin
            var selectedLon by remember { mutableStateOf(13.4050) }

            Column(
                modifier = Modifier.Companion.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                MapComponent(
                    modifier = Modifier.Companion.weight(1f),
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
                        setResult(RESULT_OK, intent)
                        finish()
                    },
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Ort bestätigen")
                }
            }
        }
    }
}