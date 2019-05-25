package com.jml.rappichallenge.models.tools

import java.util.*
import java.text.SimpleDateFormat


object DateHelper {

    private val format = "dd-MMMM-yyyy"

    fun formatDate(date : Date) : String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(date)
    }
}