package com.hornedheck.echos

import android.app.Application
import androidx.work.Configuration
import com.hornedheck.echos.di.AppComponent
import com.hornedheck.echos.di.DaggerAppComponent
import com.hornedheck.echos.utils.EchosWorkFactory
import timber.log.Timber
import javax.inject.Inject

open class EchosApp : Application(), Configuration.Provider {

    internal lateinit var appComponent: AppComponent
        private set

    @Inject
    lateinit var myWorkerFactory: EchosWorkFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent = buildAppComponent()
        appComponent.inject(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()

    protected open fun buildAppComponent(): AppComponent = DaggerAppComponent.create()

}