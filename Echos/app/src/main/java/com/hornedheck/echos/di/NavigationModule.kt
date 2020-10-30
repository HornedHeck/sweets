package com.hornedheck.echos.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone

@Module
class NavigationModule {

    private val cicerone = Cicerone.create()

    @Provides
    @FlowScope
    fun providesRouter() = cicerone.router

    @Provides
    @FlowScope
    fun provideNavHolder() = cicerone.navigatorHolder

}


