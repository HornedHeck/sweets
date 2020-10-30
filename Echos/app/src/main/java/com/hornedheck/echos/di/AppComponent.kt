package com.hornedheck.echos.di

import com.hornedheck.echos.data.di.DataModule
import com.hornedheck.echos.domain.di.DomainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {

    fun getLocalFlowComponent() : LocalFlowComponent

    fun getGlobalFlowComponent() : GlobalFlowComponent

}