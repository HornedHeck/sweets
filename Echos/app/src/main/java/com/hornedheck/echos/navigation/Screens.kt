package com.hornedheck.echos.navigation

import androidx.fragment.app.Fragment
import com.hornedheck.echos.ui.contacts.ContactsFragment
import com.hornedheck.echos.ui.login.LoginFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactsScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return ContactsFragment()
    }

}

class MessagesScreen(private val id: String) : SupportAppScreen() {

}

class LoginScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return LoginFragment()
    }
}