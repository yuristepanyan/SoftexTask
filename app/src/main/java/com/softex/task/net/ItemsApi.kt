package com.softex.task.net

import com.softex.task.model.Item
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ItemsApi {
    @GET("test.json")
    fun getItems(): Single<Response<List<Item>>>
}