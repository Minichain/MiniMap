package com.minichain.androidmapapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

  private val fireSpotsFlow = MutableStateFlow<List<FireSpot>?>(null)
  val fireSpots = fireSpotsFlow.asStateFlow()

  init {
    val httpClient = HttpClient(Android)
    viewModelScope.launch {
      val fireSpots = mutableListOf<FireSpot>()
      httpClient.get<String>(FirmsApi.getUrl()).split("\n").forEachIndexed { index, fireSpot ->
        if (index > 0) {
          val fireSpotData = fireSpot.split(",")
          fireSpots.add(
            FireSpot(
              latitude = fireSpotData[0].toDouble(),
              longitude = fireSpotData[1].toDouble(),
              brightness = fireSpotData[2].toFloat(),
              date = fireSpotData[3].toCalendarDate(DATE_STRING_FORMAT_4)
            )
          )
        }
      }
      fireSpotsFlow.emit(fireSpots)
    }
  }
}
