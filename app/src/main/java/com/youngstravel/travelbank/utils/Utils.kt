package com.youngstravel.travelbank.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun parseDate(date: String, isShortFormat: Boolean): String {
    //2021-07-13T00:00:00.000Z
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CANADA)
    val outputFormat = if(isShortFormat) {
        SimpleDateFormat("MMM dd", Locale.ENGLISH)
    } else {
        SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
    }
    var formattedDate = ""
    try {
        val parsedDate = inputFormat.parse(date)
        parsedDate?.let {
            formattedDate = outputFormat.format(parsedDate)
        }
    } catch (e: Exception) {
        Log.e("Utils", e.message.toString())
    }
    return formattedDate
}

