package com.bsi.happyplacesapp.data.model

data class Place(
    val id: Int,
    val title: String,
    val description: String,
    val imageUri: String,     // z. B. "content://..." URI als String
    val latitude: Double,
    val longitude: Double,
    val notes: String? = null
)
