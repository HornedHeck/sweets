package com.hornedheck.echos.di

import com.hornedheck.echos.ui.login.LoginComponent
import com.hornedheck.echos.ui.main.MainActivity
import dagger.Subcomponent

@FlowScope
@Subcomponent(modules = [NavigationModule::class])
interface GlobalFlowComponent {

    fun inject(to: MainActivity)

    fun loginComponent(): LoginComponent
}