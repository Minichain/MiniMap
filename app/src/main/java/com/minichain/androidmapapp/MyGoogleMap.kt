package com.minichain.androidmapapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val initialZoom = 14f
private val barcelonaCoordinates = LatLng(41.3874, 2.1686)

@Composable
fun MyGoogleMap() {
  val viewModel = MapViewModel()
  val initialZoom = initialZoom
  val initialCoordinates = barcelonaCoordinates
  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(initialCoordinates, initialZoom)
  }
  val fireSpots by viewModel.fireSpots.collectAsStateWithLifecycle()

  Box(modifier = Modifier.fillMaxSize()) {
    GoogleMap(
      modifier = Modifier.fillMaxSize(),
      cameraPositionState = cameraPositionState,
      content = {
        fireSpots?.forEach { fireSpot ->
          MarkerComposable(
            state = MarkerState(position = LatLng(fireSpot.latitude, fireSpot.longitude))
          ) {
            FireSpotMaker()
          }
        }
      },
      uiSettings = MapUiSettings(zoomControlsEnabled = false)
    )
  }
}

@Composable
fun FireSpotMaker() {
  Icon(
    imageVector = Icons.Filled.LocalFireDepartment,
    modifier = Modifier.size(32.dp),
    tint = Color.Red,
    contentDescription = null
  )
}
