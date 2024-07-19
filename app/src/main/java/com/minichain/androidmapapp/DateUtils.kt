package com.minichain.androidmapapp

import java.text.SimpleDateFormat
import java.util.Calendar

const val DATE_STRING_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DATE_STRING_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val DATE_STRING_FORMAT_3 = "yyyy"
const val DATE_STRING_FORMAT_4 = "yyyy-MM-dd"
const val DATE_STRING_FORMAT_5 = "yyyy-MM-dd HH:mm"
const val DATE_STRING_FORMAT_6 = "yyyy-MM-dd HH:mm:ss,SSS"

fun String.toCalendarDate(datePattern: String): Calendar? =
  try {
    val dateFormat = SimpleDateFormat(datePattern)
    val calendar = Calendar.getInstance()
    dateFormat.parse(this)?.let { calendar.time = it }
    calendar
  } catch (e: java.lang.Exception) {
    null
  }
