package com.example.happyplaces.data

import androidx.compose.runtime.mutableStateListOf

object PlaceRepository {
    val placeList = mutableStateListOf<Place>()

    init {
        // Kölner Dom als Standard-Ort
        placeList.add(
            Place(
                id = 1,
                title = "Kölner Dom",
                description = "Berühmte Kathedrale in Köln.",
                imageUri = "android.resource://com.example.happyplaces/drawable/koelner_dom_nachts_2013",
                latitude = 50.9413,
                longitude = 6.9583
            )
        )
    }

    fun addPlace(place: Place) {
        placeList.add(place)
    }
}
