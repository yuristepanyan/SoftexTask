package com.softex.task.di.module

import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.softex.task.arch.main.MainActivity
import com.softex.task.arch.main.MainViewModel
import com.softex.task.arch.main.MainViewModelFactory
import com.softex.task.db.ItemDao
import com.softex.task.di.ActivityScope
import com.softex.task.helper.DateTimeFormat
import com.softex.task.net.ItemsApi
import com.softex.task.util.ResponseErrorHandler
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun provideViewModel(
        activity: MainActivity,
        api: ItemsApi,
        errorHandler: ResponseErrorHandler,
        gson: Gson,
        itemDao: ItemDao
    ): MainViewModel {
        return ViewModelProviders.of(
            activity,
            MainViewModelFactory(api, errorHandler, gson, itemDao, CompositeDisposable())
        ).get(MainViewModel::class.java)
    }
}