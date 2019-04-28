package com.softex.task.helper

interface DateTimeFormat {

    /**
     * Converts the API received date to the UI style
     */
    fun getAppDate(date: String): String
}