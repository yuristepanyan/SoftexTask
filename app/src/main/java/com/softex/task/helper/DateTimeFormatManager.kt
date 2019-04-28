package com.softex.task.helper

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

enum class Format(val format: String) {
    APP_DATE_FORMAT("dd MMM yyyy HH:mm:ss"),
    API_DATE_FORMAT("yyyy-MM-dd HH:mm:ss.SSS")
}

class DateTimeFormatManager @Inject constructor() : DateTimeFormat {
    override fun getAppDate(date: String): String {
        return SimpleDateFormat(Format.APP_DATE_FORMAT.format, Locale.getDefault())
            .format(SimpleDateFormat(Format.API_DATE_FORMAT.format, Locale.getDefault()).parse(date))
    }
}