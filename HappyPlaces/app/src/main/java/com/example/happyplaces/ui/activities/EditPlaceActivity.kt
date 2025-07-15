package com.example.happyplaces.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.happyplaces.data.PlaceRepository
import com.example.happyplaces.ui.screens.EditPlaceScreen
import com.example.happyplaces.ui.theme.HappyPlacesTheme

class EditPlaceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val placeId = intent.getIntExtra("place_id", -1)
        val place = PlaceRepository.placeList.find { it.id == placeId }

        setContent {
            HappyPlacesTheme {
                if (place != null) {
                    EditPlaceScreen(
                        placeToEdit = place,
                        onPlaceUpdated = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}
