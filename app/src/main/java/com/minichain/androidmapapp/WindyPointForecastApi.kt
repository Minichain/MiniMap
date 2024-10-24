package com.minichain.androidmapapp

import com.google.android.gms.maps.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

object WindyPointForecastApi {

  private const val apiKey = BuildConfig.WINDY_POINT_FORECAST_API_KEY
  private val barcelonaCoordinates = LatLng(41.3874, 2.1686)
  private val requestBodyAsJsonString =
    "{" +
      "\"lat\": ${barcelonaCoordinates.latitude}," +
      "\"lon\": ${barcelonaCoordinates.longitude}," +
      "\"model\": \"gfs\"," +
      "\"parameters\": [\"wind\", \"temp\"]," +
      "\"levels\": [\"surface\", \"800h\"]," +
      "\"key\": \"${apiKey}\"" +
    "}"

  suspend fun getData(httpClient: HttpClient): WeatherData {
    val apiUrl = "https://api.windy.com/api/point-forecast/v2"
    println("AndroidMapAppLog: sending POST request with body: ${requestBodyAsJsonString}...")
    var weatherData = WeatherData()
    httpClient.post {
      url(apiUrl)
      contentType(ContentType.Application.Json)
      setBody(requestBodyAsJsonString)
    }.let { response ->
      when (response.status.value) {
        in 200..299 -> {
          println("AndroidMapAppLog: Successful response! value: ${response.status.value}")
          response.body<String>().let { content ->
            println("AndroidMapAppLog: Response content: $content")
            Json.parseToJsonElement(content).let { jsonElement ->
              (jsonElement as JsonObject).let { jsonObject ->
//                println("AndroidMapAppLog: wind_u-surface data: ${jsonObject["wind_u-surface"]}")
//                println("AndroidMapAppLog: wind_v-surface data: ${jsonObject["wind_v-surface"]}")
//                println("AndroidMapAppLog: temp-surface data: ${jsonObject["temp-surface"]}")

                val windUSurface = jsonObject["wind_u-surface"].toString().getFirstValue()
                val windVSurface = jsonObject["wind_v-surface"].toString().getFirstValue()
                val temperatureOnSurface = jsonObject["temp-surface"].toString().getFirstValue()
                weatherData = WeatherData(
                  windOnSurface = Pair(windUSurface, windVSurface),
                  temperatureOnSurface = temperatureOnSurface
                )
              }
            }
          }
        }
        else -> {
          println("AndroidMapAppLog: Failed response! value: ${response.status.value}")
        }
      }
    }
    return weatherData
  }

  private fun String.getFirstValue(): Double =
    this.substring(1, this.length - 1).split(",").let { values ->
      return values[0].toDouble()
    }
}