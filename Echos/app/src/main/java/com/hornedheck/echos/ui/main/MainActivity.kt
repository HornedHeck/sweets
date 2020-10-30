package com.hornedheck.echos.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseActivity
import com.hornedheck.echos.navigation.EchosNavigator
import com.hornedheck.echos.navigation.GlobalNavigation
import com.hornedheck.echos.navigation.LoginScreen
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    @Inject
    lateinit var navigation: GlobalNavigation

    private val navigator = EchosNavigator(this, R.id.flRootContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.router.newRootScreen(LoginScreen())
    }

    override fun inject() {
        appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigation.navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigation.navHolder.removeNavigator()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_logout) {
            AuthUI.getInstance().signOut(this)
            navigation.router.newRootScreen(LoginScreen())
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        navigation.router.exit()
    }

}