package com.hornedheck.echos.di

import com.hornedheck.echos.data.di.DataModule
import com.hornedheck.echos.ui.MainActivity
import com.hornedheck.echos.ui.contacts.ContactsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(to: MainActivity)

    fun inject(to: ContactsFragment)

}