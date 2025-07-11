package com.example.happyplaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.happyplaces.ui.AddPlaceActivity
import com.example.happyplaces.ui.SelectLocationActivity
import com.example.happyplaces.ui.theme.HappyPlacesTheme

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

    Button(onClick = {
        val intent = Intent(context, SelectLocationActivity::class.java)
        launcher.launch(intent)
    }) {
        Text("Neuen Ort hinzufügen")
    }
}
