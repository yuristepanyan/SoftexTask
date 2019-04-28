package com.softex.task.extensions

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.softex.task.model.ErrorResponse
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Response

fun <T> Observable<Response<T>>.handleError(errorData: MutableLiveData<String>?, gson: Gson): Disposable {
    return this.filter { !it.isSuccessful }
        .subscribe {
            errorData?.postValue(
                it.errorBody()?.let { error ->
                    ErrorResponse(error.string(), gson).getErrorMessage()
                } ?: ""
            )
        }
}