package com.minichain.androidmapapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

  private val httpClient = HttpClient(Android) {
    install(ContentNegotiation) {
      json()
    }
  }

  private val fireSpotsFlow = MutableStateFlow<List<FireSpot>?>(null)
  val fireSpots = fireSpotsFlow.asStateFlow()

  private val weatherDataFlow = MutableStateFlow<WeatherData?>(null)
  val weatherData = weatherDataFlow.asStateFlow()

  init {
    updateFireSpots()
    updatePointForecast()
  }

  private fun updateFireSpots() {
    viewModelScope.launch {
      fireSpotsFlow.emit(FirmsApi.getFireSpots(httpClient))
    }
  }

  private fun updatePointForecast() {
    viewModelScope.launch {
      weatherDataFlow.emit(WindyPointForecastApi.getData(httpClient))
    }
  }
}
