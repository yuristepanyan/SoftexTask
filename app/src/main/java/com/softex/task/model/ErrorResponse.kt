package com.softex.task.model

import com.google.gson.Gson

data class ErrorResponse(val error: ErrorModel) {

    constructor(json: String, gson: Gson) : this(gson.fromJson(json, ErrorResponse::class.java).error)

    fun getErrorMessage(): String {
        return error.message
    }
}