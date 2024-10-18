package com.minichain.androidmapapp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.content.VersionCheckResult

object FirmsApi {

  private const val apiKey = BuildConfig.FIRMS_API_KEY
  private const val europeCoordinates = "-15,33,35,67"
  private const val dayRange = "1"

  suspend fun getFireSpots(httpClient: HttpClient): List<FireSpot> {
    val apiUrl = "https://firms.modaps.eosdis.nasa.gov/api/area/csv/$apiKey/${Source.MODIS_NRT.string}/$europeCoordinates/$dayRange"
    println("AndroidMapAppLog: FIRMS API URL: $apiUrl")
    val fireSpots = mutableListOf<FireSpot>()
    val response = httpClient.get(apiUrl)
    when (response.status.value) {
      in 200..299 -> {
        println("AndroidMapAppLog: Successful response! value: ${response.status.value}")
        response.body<String>().split("\n").forEachIndexed { index, fireSpot ->
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
      }
      else -> {
        println("AndroidMapAppLog: Failed response! value: ${response.status.value}")
      }
    }
    return fireSpots.toList()
  }

  private enum class Source(val string: String) {
    LANDSAT_NRT("LANDSAT_NRT"),
    MODIS_NRT("MODIS_NRT"),
    MODIS_SP("MODIS_SP"),
    VIIRS_NOAA20_NRT("VIIRS_NOAA20_NRT"),
    VIIRS_NOAA21_NRT("VIIRS_NOAA21_NRT"),
    VIIRS_SNPP_NRT("VIIRS_SNPP_NRT"),
    VIIRS_SNPP_SP("VIIRS_SNPP_SP")
  }
}
