package com.minichain.androidmapapp

import com.google.android.gms.maps.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

object WindyPointForecastApi {

  private const val apiKey = BuildConfig.WINDY_POINT_FORECAST_API_KEY
  private val barcelonaCoordinates = LatLng(41.3874, 2.1686)
  private val requestBodyAsJsonString =
    "{" +
      "\"lat\": ${barcelonaCoordinates.latitude}," +
      "\"lon\": ${barcelonaCoordinates.longitude}," +
      "\"model\": \"gfs\"," +
      "\"parameters\": [\"wind\", \"dewpoint\", \"rh\", \"pressure\"]," +
      "\"levels\": [\"surface\", \"800h\", \"300h\"]," +
      "\"key\": \"${apiKey}\"" +
    "}"

  suspend fun getData(httpClient: HttpClient): String {
    val apiUrl = "https://api.windy.com/api/point-forecast/v2"
    println("AndroidMapAppLog: send post request with body: ${requestBodyAsJsonString}")
    httpClient.post {
      url(apiUrl)
      contentType(ContentType.Application.Json)
      setBody(requestBodyAsJsonString)
    }.let { response ->
      println("AndroidMapAppLog: response: ${response}")
      println("AndroidMapAppLog: response status: ${response.status}")
      println("AndroidMapAppLog: response call: ${response.call}")
    }
    return apiUrl
  }
}