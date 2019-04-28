package com.softex.task.di.component

import com.softex.task.arch.main.MainActivity
import com.softex.task.di.ActivityScope
import com.softex.task.di.module.MainModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(
    modules = [
        MainModule::class
    ]
)
@ActivityScope
interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<MainActivity>()
}