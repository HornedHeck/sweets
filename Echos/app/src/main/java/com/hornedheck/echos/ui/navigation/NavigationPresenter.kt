package com.hornedheck.echos.ui.navigation

import com.hornedheck.echos.R
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.repo.UserRepo
import com.hornedheck.echos.navigation.ContactsScreen
import com.hornedheck.echos.navigation.GlobalNavigation
import com.hornedheck.echos.navigation.LocalNavigation
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

    fun navigate(to: Int): Boolean {
        if (to == lastNav) return true
        return when (to) {
            R.id.menu_contacts -> {
                navigation.router.replaceScreen(ContactsScreen())
                lastNav = to
                true
            }
            R.id.menu_settings -> {
                viewState.showError("Not implemented", "Not implemented")
                false
            }
            R.id.menu_logout -> {
                viewState.showError("Not implemented", "Not implemented")
                false
            }
            else -> false
        }

    }


}