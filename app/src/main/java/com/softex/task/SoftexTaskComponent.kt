package com.softex.task

import android.app.Application
import com.softex.task.di.module.ActivityBuilder
import com.softex.task.di.module.DatabaseModule
import com.softex.task.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        SoftexTaskModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ActivityBuilder::class
    ]
)
interface SoftexTaskComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun networkModule(module: NetworkModule): Builder

        fun build(): SoftexTaskComponent
    }
}