package com.softex.task

import com.softex.task.di.module.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SoftexTest: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerSoftexTaskComponent.builder()
            .application(this)
            .networkModule(NetworkModule())
            .build()
        component.inject(this)
        return component
    }

    companion object {
        lateinit var component: SoftexTaskComponent
    }
}