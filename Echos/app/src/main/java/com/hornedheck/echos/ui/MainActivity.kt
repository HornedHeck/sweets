package com.hornedheck.echos.ui

import android.os.Bundle
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.ui.contacts.ContactsFragment
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.flRootContainer, ContactsFragment())
            .commit()
    }
}