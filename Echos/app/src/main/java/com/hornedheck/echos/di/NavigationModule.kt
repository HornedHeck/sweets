package com.hornedheck.echos.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone = Cicerone.create()


    @Provides
    @Singleton
    fun providesRouter() = cicerone.router

    @Provides
    @Singleton
    fun provideNavHolder() = cicerone.navigatorHolder

}