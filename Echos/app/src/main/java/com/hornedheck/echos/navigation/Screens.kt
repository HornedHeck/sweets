package com.hornedheck.echos.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.hornedheck.echos.ui.contacts.ContactsFragment
import com.hornedheck.echos.ui.login.LoginActivity
import com.hornedheck.echos.ui.main.MainActivity
import com.hornedheck.echos.ui.messages.MessagesFragment
import com.hornedheck.echos.ui.navigation.NavigationFragment
import com.hornedheck.echos.ui.settings.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactsScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return ContactsFragment()
    }

}

class MessagesScreen(private val id: String) : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return MessagesFragment.getInstance(id)
    }
}

class LoginScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context): Intent? {
        return Intent(context, LoginActivity::class.java)
    }
}

class NavigationHostScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return NavigationFragment()
    }
}

class MainScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context): Intent? {
        return Intent(context, MainActivity::class.java)
    }
}

class SettingsScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return SettingsFragment()
    }
}