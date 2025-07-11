package com.example.happyplaces.data

data class Place(
    val id: Int = 0,
    val title: String,
    val description: String,
    val imageUri: String?,
    val latitude: Double,
    val longitude: Double,
    var notes: String? = null
)
