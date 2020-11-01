package com.hornedheck.echos.ui.navigation

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.repo.UserRepo
import com.hornedheck.echos.navigation.*
import javax.inject.Inject

class NavigationPresenter @Inject constructor(
    private val navigation: LocalNavigation,
    private val globalNavigation: GlobalNavigation,
    private val userRepo: UserRepo,
) : BasePresenter<NavigationView>() {

    private var lastNav = 0

    fun getUserInfo() {
        userRepo.getUser().subscribe(viewState::setUserInfo, viewState::showError)
    }

    fun initialNavigation(context: Context) {
        navigate(
            if (lastNav == 0) {
                R.id.menu_contacts
            } else {
                lastNav
            }, context
        )
    }

    fun navigate(to: Int, context: Context): Boolean {
        if (to == lastNav) return true
        return when (to) {
            R.id.menu_contacts -> {
                navigation.router.replaceScreen(ContactsScreen())
                lastNav = to
                true
            }
            R.id.menu_settings -> {
                navigation.router.replaceScreen(SettingsScreen())
                lastNav = to
                true
            }
            R.id.menu_logout -> {
                AuthUI.getInstance().signOut(context)
                globalNavigation.router.newRootScreen(LoginScreen())
                false
            }
            else -> false
        }

    }


}