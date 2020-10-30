package com.hornedheck.echos

import android.app.Application
import com.hornedheck.echos.di.AppComponent
import com.hornedheck.echos.di.DaggerAppComponent
import timber.log.Timber

class EchosApp : Application() {

    internal lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.create()
    }

}