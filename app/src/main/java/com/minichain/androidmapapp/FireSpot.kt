package com.minichain.androidmapapp

import java.util.Calendar

data class FireSpot(
  val latitude: Double,
  val longitude: Double,
  val brightness: Float,
  val date: Calendar?
)
