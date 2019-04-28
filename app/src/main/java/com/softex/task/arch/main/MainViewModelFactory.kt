package com.softex.task.arch.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.softex.task.db.ItemDao
import com.softex.task.net.ItemsApi
import com.softex.task.util.ResponseErrorHandler
import io.reactivex.disposables.CompositeDisposable

/**
 * The Factory for the MainViewModel class
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val api: ItemsApi,
    private val errorHandler: ResponseErrorHandler,
    private val gson: Gson,
    private val itemDao: ItemDao,
    private val disposable: CompositeDisposable
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(api, errorHandler, gson, itemDao, disposable) as T
    }
}