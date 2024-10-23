package com.minichain.androidmapapp

data class WeatherData(
  val windOnSurface: Pair<Double, Double> = Pair(-1.0, -1.0),
  val temperatureOnSurface: Double = -1.0
)