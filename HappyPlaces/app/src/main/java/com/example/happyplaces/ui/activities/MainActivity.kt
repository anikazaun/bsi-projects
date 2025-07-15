package com.example.happyplaces.ui.activities

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.happyplaces.data.Place
import com.example.happyplaces.data.PlaceRepository
import com.example.happyplaces.ui.composables.PlaceCard
import com.example.happyplaces.ui.composables.PlaceDetailView
import com.example.happyplaces.ui.screens.MainScreen
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

