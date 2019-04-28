package com.softex.task.di.module

import android.app.Activity
import com.softex.task.arch.main.MainActivity
import com.softex.task.di.component.MainComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMain(builder: MainComponent.Builder): AndroidInjector.Factory<out Activity>
}