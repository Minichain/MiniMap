package com.minichain.androidmapapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

  private val httpClient = HttpClient(Android) {
    install(ContentNegotiation) {
      gson()
    }
  }

  private val fireSpotsFlow = MutableStateFlow<List<FireSpot>?>(null)
  val fireSpots = fireSpotsFlow.asStateFlow()

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
      WindyPointForecastApi.getData(httpClient)
    }
  }
}
