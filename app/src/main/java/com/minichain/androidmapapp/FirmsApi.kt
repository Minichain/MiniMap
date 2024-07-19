package com.minichain.androidmapapp

object FirmsApi {

  private const val apiKey = BuildConfig.FIRMS_API_KEY
  private const val europeCoordinates = "-15,33,35,67"
  private const val dayRange = "1"

  fun getUrl(): String {
    val apiUrl = "https://firms.modaps.eosdis.nasa.gov/api/area/csv/$apiKey/${Source.MODIS_NRT.string}/$europeCoordinates/$dayRange"
    println("AndroidMapAppLog: apiURL: $apiUrl")
    return apiUrl
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
