package com.example.happyplaces.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.happyplaces.ui.composables.MapComponent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.location.Location
import android.location.LocationManager
import android.content.Context

class SelectLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            var selectedLat by remember { mutableStateOf(52.5200) }
            var selectedLon by remember { mutableStateOf(13.4050) }
            var mapViewRef by remember { mutableStateOf<MapView?>(null) }
            var markerRef by remember { mutableStateOf<Marker?>(null) }

            // Standort-Berechtigung anfordern
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    val location = getLastKnownLocation(context)
                    location?.let {
                        val geoPoint = GeoPoint(it.latitude, it.longitude)
                        selectedLat = geoPoint.latitude
                        selectedLon = geoPoint.longitude
                        mapViewRef?.controller?.setCenter(geoPoint)
                        markerRef?.position = geoPoint
                        mapViewRef?.invalidate()
                    }
                }
            }

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            when {
                                ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED -> {
                                    val location = getLastKnownLocation(context)
                                    location?.let {
                                        val geoPoint = GeoPoint(it.latitude, it.longitude)
                                        selectedLat = geoPoint.latitude
                                        selectedLon = geoPoint.longitude
                                        mapViewRef?.controller?.setCenter(geoPoint)
                                        markerRef?.position = geoPoint
                                        mapViewRef?.invalidate()
                                    }
                                }
                                else -> {
                                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 64.dp) // <-- Button leicht nach oben geschoben
                    ) {
                        Icon(Icons.Filled.LocationOn, contentDescription = "Meine Position")
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    MapComponent(
                        modifier = Modifier.weight(1f),
                        onLocationSelected = { lat, lon ->
                            selectedLat = lat
                            selectedLon = lon
                        },
                        onMapReady = { mapView, marker ->
                            mapViewRef = mapView
                            markerRef = marker
                        }
                    )

                    Button(
                        onClick = {
                            val intent = Intent().apply {
                                putExtra("latitude", selectedLat)
                                putExtra("longitude", selectedLon)
                            }
                            setResult(RESULT_OK, intent)
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

    private fun getLastKnownLocation(context: Context): Location? {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        for (provider in providers.reversed()) {
            try {
                val location = locationManager.getLastKnownLocation(provider)
                if (location != null) return location
            } catch (_: SecurityException) {
            }
        }
        return null
    }
}
