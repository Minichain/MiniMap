package com.minichain.androidmapapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun MyGoogleMap() {
  val viewModel = MapViewModel()
  val initialZoom = 14f
  val initialCoordinates = LatLng(41.3874, 2.1686)
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
  Box(
    modifier = Modifier
      .size(32.dp)
      .clip(CircleShape)
      .background(Color.Red)
      .padding(2.dp)
      .clip(CircleShape)
      .background(Color.White)
      .padding(2.dp)
      .clip(CircleShape)
      .background(Color.Red)
  )
}
