package com.example.criticalnewstest.utilities

import android.util.Log
import androidx.core.net.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun formatDate(inputDate: String, inputFormat: String, outputFormat: String): String {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return try {
            val date = inputDateFormat.parse(inputDate)
            outputDateFormat.format(date)
        } catch (e: ParseException) {
            Log.e("DateUtils", "Error parsing date: ${e.message}")
            inputDate
        }
    }
}