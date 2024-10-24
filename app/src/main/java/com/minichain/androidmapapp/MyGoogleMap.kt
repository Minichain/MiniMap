package com.minichain.androidmapapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val initialZoom = 14f
private val barcelonaCoordinates = LatLng(41.3874, 2.1686)

@Composable
fun MyGoogleMap(
  viewModel: MapViewModel
) {
  val initialZoom = initialZoom
  val initialCoordinates = barcelonaCoordinates
  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(initialCoordinates, initialZoom)
  }
  val fireSpots by viewModel.fireSpots.collectAsStateWithLifecycle()
  val weatherData by viewModel.weatherData.collectAsStateWithLifecycle()

  Box(modifier = Modifier.fillMaxSize()) {
    GoogleMap(
      modifier = Modifier.fillMaxSize(),
      cameraPositionState = cameraPositionState,
      properties = myGoogleMapProperties(),
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
    weatherData?.let { weatherData ->
      WeatherData(
        modifier = Modifier
          .padding(8.dp)
          .align(Alignment.TopStart)
          .background(MaterialTheme.colorScheme.background),
        weatherData = weatherData
      )
    }
  }
}

@Composable
private fun WeatherData(
  modifier: Modifier,
  weatherData: WeatherData
) {
  Column(
    modifier = modifier
  ) {
    Row {
      Text("Wind: ")
      Text("(${String.format("%.2f", weatherData.windOnSurface.first)}, ${String.format("%.2f", weatherData.windOnSurface.second)})")
      Text("m/s")
    }
    Row {
      Text("Temperature: ")
      Text("${String.format("%.2f", weatherData.temperatureOnSurface)}")
      Text("ºK")
    }
  }
}

@Composable
private fun myGoogleMapProperties() =
  MapProperties(
    isMyLocationEnabled = true
  )

@Composable
private fun FireSpotMaker() {
  Icon(
    imageVector = Icons.Filled.LocalFireDepartment,
    modifier = Modifier.size(32.dp),
    tint = Color.Red,
    contentDescription = null
  )
}
