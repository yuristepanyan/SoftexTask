package com.softex.task

import android.app.Application
import android.content.Context
import com.softex.task.di.component.MainComponent
import com.softex.task.helper.DateTimeFormat
import com.softex.task.helper.DateTimeFormatManager
import com.softex.task.util.ResponseErrorHandler
import com.softex.task.util.ResponseErrorHandlerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    subcomponents = [
        MainComponent::class
    ]
)
abstract class SoftexTaskModule {
    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindErrorHandler(errorHandler: ResponseErrorHandlerImpl): ResponseErrorHandler

    @Binds
    @Singleton
    abstract fun bindDateTimeFormat(dateTimeFormat: DateTimeFormatManager): DateTimeFormat

}