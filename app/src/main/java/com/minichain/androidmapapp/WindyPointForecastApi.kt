package com.minichain.androidmapapp

import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

object WindyPointForecastApi {

  private const val apiKey = BuildConfig.WINDY_POINT_FORECAST_API_KEY
  private val barcelonaCoordinates = LatLng(41.3874, 2.1686)
  private val requestBodyAsJsonObject = JsonParser.parseString(
    "{" +
      "\"lat\": ${barcelonaCoordinates.latitude}," +
      "\"lon\": ${barcelonaCoordinates.longitude}," +
      "\"model\": \"iconEU\"," +
      "\"parameters\": [\"wind\", \"dewpoint\", \"rh\", \"pressure\"]," +
      "\"levels\": [\"surface\", \"800h\", \"300h\"]," +
      "\"key\": \"${apiKey}\"" +
    "}"
    ).asJsonObject

  suspend fun getData(httpClient: HttpClient): String {
    val apiUrl = "https://api.windy.com/api/point-forecast/v2"
    httpClient.post {
      url(apiUrl)
      contentType(ContentType.Application.Json)
      setBody(requestBodyAsJsonObject)
//      body = MultiPartFormDataContent(
//        parts = formData {
//          append("lat", barcelonaCoordinates.latitude)
//          append("lon", barcelonaCoordinates.longitude)
//          append("model", "iconEU")
//          append("parameters", "wind")
//          append("levels", "surface")
//          append("key", apiKey)
//        }
//      )
    }.let { response ->
      print("AndroidMapAppLog response: ${response}")
    }
    return apiUrl
  }
}