package com.hornedheck.echos.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseFragment
import com.hornedheck.echos.di.LocalFlowComponent
import com.hornedheck.echos.navigation.EchosNavigator
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_navigation.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NavigationFragment : BaseFragment(R.layout.fragment_navigation), NavigationView {

    @Inject
    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    @ProvidePresenter
    fun provide() = presenter

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val navigator by lazy {
        EchosNavigator(
            requireActivity(),
            R.id.flFragmentContainer,
            childFragmentManager
        )
    }

    override val localFlowComponent: LocalFlowComponent
        get() = appComponent.getLocalFlowComponent()

    override fun inject() {
        localFlowComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }
        val toggle = ActionBarDrawerToggle(requireActivity(),
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

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

}