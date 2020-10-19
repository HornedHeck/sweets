package com.hornedheck.echos

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hornedheck.echos.di.AppComponent
import com.hornedheck.echos.di.DaggerAppComponent

class EchosApp : Application() {

    internal lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

}

internal val AppCompatActivity.appComponent: AppComponent
    get() = (application as EchosApp).appComponent

internal val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as EchosApp).appComponent