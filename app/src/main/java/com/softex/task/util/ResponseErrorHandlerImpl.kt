package com.softex.task.util

import android.content.Context
import com.google.gson.Gson
import com.softex.task.R
import com.softex.task.model.ErrorModel
import com.softex.task.model.ErrorResponse
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Response.error
import java.net.UnknownHostException
import javax.inject.Inject

class ResponseErrorHandlerImpl @Inject constructor(private val gson: Gson,
                                                   private val context: Context) : ResponseErrorHandler {

    private val jsonMediaType = MediaType.parse("application/json")

    override fun <T> toResponseError(throwable: Throwable): Response<T> {
        when (throwable) {
            is UnknownHostException ->
                return error(
                    600, ResponseBody.create(
                        jsonMediaType,
                        gson.toJson(ErrorResponse(ErrorModel(context.getString(R.string.no_internet))))
                    )
                )
        }
        return error(
            600, ResponseBody.create(
                jsonMediaType,
                gson.toJson(ErrorResponse(ErrorModel(throwable.localizedMessage)))
            )
        )
    }

}