package com.example.happyplaces.ui.composables

import android.view.MotionEvent
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    onLocationSelected: (latitude: Double, longitude: Double) -> Unit,
    onMapReady: (MapView, Marker) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(
            context,
            PreferenceManager.getDefaultSharedPreferences(context)
        )
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            MapView(ctx).apply {
                setMultiTouchControls(true)
                controller.setZoom(15.0)
                val startPoint = GeoPoint(52.5200, 13.4050)
                controller.setCenter(startPoint)

                val marker = Marker(this).apply {
                    position = startPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                overlays.add(marker)

                onMapReady(this, marker)

                setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        val proj = projection
                        val geoPoint = proj.fromPixels(event.x.toInt(), event.y.toInt()) as GeoPoint
                        marker.position = geoPoint
                        onLocationSelected(geoPoint.latitude, geoPoint.longitude)
                        invalidate()
                        true
                    } else {
                        false
                    }
                }
            }
        }
    )
}
