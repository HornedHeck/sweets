package com.hornedheck.restfultimer.utils

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RestfulDayApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RestfulDayApp)
            androidLogger(level = Level.ERROR)
            modules(dataModule, viewModelsModule)
        }

    }
}