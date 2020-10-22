package com.hornedheck.echos.di

import com.hornedheck.echos.data.di.DataModule
import com.hornedheck.echos.domain.di.DomainModule
import com.hornedheck.echos.ui.contacts.ContactsFragment
import com.hornedheck.echos.ui.login.LoginFragment
import com.hornedheck.echos.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, NavigationModule::class, DomainModule::class])
interface AppComponent {

    fun inject(to: MainActivity)

    fun inject(to: ContactsFragment)

    fun inject(to: LoginFragment)

}