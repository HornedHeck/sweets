package com.hornedheck.echos.navigation

import androidx.fragment.app.Fragment
import com.hornedheck.echos.ui.contacts.ContactsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactsScreen() : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return ContactsFragment()
    }

}