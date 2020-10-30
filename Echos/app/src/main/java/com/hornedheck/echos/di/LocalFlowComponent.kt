package com.hornedheck.echos.di

import com.hornedheck.echos.ui.contacts.ContactsFragment
import com.hornedheck.echos.ui.messages.MessagesFragment
import com.hornedheck.echos.ui.navigation.NavigationFragment
import dagger.Subcomponent

@FlowScope
@Subcomponent(modules = [LocalNavigationModule::class])
interface LocalFlowComponent {

    fun inject(to: NavigationFragment)

    fun inject(to: ContactsFragment)

    fun inject(to: MessagesFragment)

}