package com.hornedheck.echos.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.firebase.ui.auth.AuthUI
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseActivity
import com.hornedheck.echos.domain.models.User
import com.hornedheck.echos.navigation.EchosNavigator
import com.hornedheck.echos.navigation.LoginScreen
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main), NavigationView {

    @Inject
    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    @ProvidePresenter
    fun provide() = presenter

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val navigator = EchosNavigator(this, R.id.flFragmentContainer)

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { item ->
            presenter.navigate(item.itemId , this).also {
                if (it) drawerLayout.closeDrawers()
            }
        }

        navView.setCheckedItem(R.id.menu_contacts)
        presenter.navigate(R.id.menu_contacts , this)

        presenter.getUserInfo()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) return true

        if (item.itemId == R.id.menu_logout) {
            AuthUI.getInstance().signOut(this)
            router.newRootScreen(LoginScreen())
            return true
        }



        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        router.exit()
    }

    override fun setUserInfo(user: User) {
        try {
            tvName.text = user.name
            tvLink.text = user.link
        } catch (e: Exception) {

        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
//        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        toggle.onConfigurationChanged(newConfig)
    }
}