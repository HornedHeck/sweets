package com.hornedheck.echos.di

import com.hornedheck.echos.navigation.GlobalNavigation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    private val navigation = GlobalNavigation()

    @Provides
    @Singleton
    fun navigation() = navigation

}


