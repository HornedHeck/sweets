package com.hornedheck.echos.di

import com.hornedheck.echos.data.di.DataModule
import com.hornedheck.echos.ui.main.MainActivity
import com.hornedheck.echos.ui.contacts.ContactsFragment
import com.hornedheck.echos.ui.login.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(to: MainActivity)

    fun inject(to: ContactsFragment)

    fun inject(to: LoginFragment)

}