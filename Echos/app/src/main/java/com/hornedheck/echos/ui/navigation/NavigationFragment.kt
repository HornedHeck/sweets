package com.hornedheck.echos.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseFragment
import com.hornedheck.echos.domain.models.User
import com.hornedheck.echos.navigation.EchosNavigator
import com.hornedheck.echos.navigation.LocalNavigation
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.nav_header_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NavigationFragment : BaseFragment(R.layout.fragment_navigation), NavigationView {

    @Inject
    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    @ProvidePresenter
    fun provide() = presenter

    @Inject
    lateinit var navigation: LocalNavigation

    private val navigator by lazy {
        EchosNavigator(
            requireActivity(),
            R.id.flFragmentContainer,
            childFragmentManager
        )
    }

    override val flowComponent by lazy { appComponent.getLocalFlowComponent() }

    override fun inject() {
        flowComponent.inject(this)
        presenter.getUserInfo()
    }

    override fun onResume() {
        super.onResume()
        navigation.navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigation.navHolder.removeNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
        navView.setNavigationItemSelectedListener { item ->
            presenter.navigate(item.itemId).also {
                if (it) drawerLayout.closeDrawers()
            }
        }
        navView.setCheckedItem(R.id.menu_contacts)
        presenter.navigate(R.id.menu_contacts)
    }

    override fun setUserInfo(user: User) {
        tvName?.text = user.name
        tvLink?.text = user.link
    }
}