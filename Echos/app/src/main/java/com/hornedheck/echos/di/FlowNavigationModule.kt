package com.hornedheck.echos.di

import com.hornedheck.echos.navigation.LocalNavigation
import dagger.Module
import dagger.Provides

@Module
class FlowNavigationModule {

    private val navigation = LocalNavigation()

    @Provides
    @FlowScope
    fun navigation() = navigation

}