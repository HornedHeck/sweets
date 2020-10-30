package com.hornedheck.echos.di

import com.hornedheck.echos.ui.contacts.ContactsFragment
import com.hornedheck.echos.ui.navigation.NavigationFragment
import dagger.Subcomponent

@FlowScope
@Subcomponent(modules = [FlowNavigationModule::class])
interface FlowNavigationComponent {

    fun inject(to: NavigationFragment)

    fun inject(to: ContactsFragment)

}