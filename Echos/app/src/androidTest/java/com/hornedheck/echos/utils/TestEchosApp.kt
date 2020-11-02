package com.hornedheck.echos.utils

import com.hornedheck.echos.EchosApp
import com.hornedheck.echos.di.AppComponent
import com.hornedheck.echos.di.DaggerAppComponent
import com.hornedheck.echos.mocks.di.TestDataModule

class TestEchosApp : EchosApp() {

    override fun buildAppComponent(): AppComponent =
        DaggerAppComponent.builder()
            .dataModule(TestDataModule())
            .build()
}