package com.hornedheck.echos.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseFragment(R.layout.fragment_navigation) {

    override fun inject() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }
        val toggle = ActionBarDrawerToggle(requireActivity(),
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

    }

}