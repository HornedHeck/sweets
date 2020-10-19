package com.hornedheck.echos.ui

import android.os.Bundle
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.BaseActivity
import com.hornedheck.echos.navigation.ContactsScreen
import com.hornedheck.echos.navigation.EchosNavigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val navigator = EchosNavigator(this, R.id.flRootContainer)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.newRootScreen(ContactsScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

}