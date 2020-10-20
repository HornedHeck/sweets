package com.hornedheck.echos.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.BaseActivity
import com.hornedheck.echos.navigation.EchosNavigator
import com.hornedheck.echos.navigation.LoginScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val navigator = EchosNavigator(this, R.id.flRootContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.newRootScreen(LoginScreen())
    }

    override fun inject() {
        appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_logout) {
//            FirebaseAuth.getInstance().signOut()
            AuthUI.getInstance().signOut(this)
            router.newRootScreen(LoginScreen())
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}