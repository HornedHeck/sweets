package com.hornedheck.echos.di

import com.hornedheck.echos.data.di.DataModule
import com.hornedheck.echos.domain.di.DomainModule
import com.hornedheck.echos.ui.login.LoginFragment
import com.hornedheck.echos.ui.main.MainActivity
import com.hornedheck.echos.ui.messages.MessagesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, NavigationModule::class])
interface AppComponent {

    fun getLocalFlowComponent(): FlowNavigationComponent

    fun inject(to: LoginFragment)

    fun inject(to: MainActivity)

    fun inject(to: MessagesFragment)

}