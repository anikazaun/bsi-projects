package com.bsi.happyplacesapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.bsi.happyplacesapp.data.model.Place

class PlaceViewModel : ViewModel() {

    // Liste der Orte (wird von der UI beobachtet)
    private val _places = mutableStateListOf<Place>()
    val places: List<Place> = _places

    // Ort hinzufügen
    fun addPlace(place: Place) {
        _places.add(place)
    }

    // (optional) Ort löschen
    fun removePlace(place: Place) {
        _places.remove(place)
    }
}
