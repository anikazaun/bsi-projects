package com.example.happyplaces.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.happyplaces.ui.screens.AddPlaceScreen
import com.example.happyplaces.ui.theme.HappyPlacesTheme

class AddPlaceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val latitude = intent.getDoubleExtra("latitude", 52.5200)
        val longitude = intent.getDoubleExtra("longitude", 13.4050)

        setContent {
            HappyPlacesTheme {
                AddPlaceScreen(startLatitude = latitude, startLongitude = longitude)
            }
        }
    }
}