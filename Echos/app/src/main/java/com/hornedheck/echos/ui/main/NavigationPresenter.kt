package com.hornedheck.echos.ui.main

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.repo.UserRepo
import com.hornedheck.echos.navigation.ContactsScreen
import com.hornedheck.echos.navigation.LoginScreen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NavigationPresenter @Inject constructor(
    private val router: Router,
    private val userRepo: UserRepo,
) : BasePresenter<NavigationView>() {

    fun getUserInfo() {
        userRepo.getUser().subscribe(viewState::setUserInfo, viewState::showError)
    }

    fun navigate(to: Int, context: Context): Boolean {
        return when (to) {
            R.id.menu_contacts -> {
                router.replaceScreen(ContactsScreen())
                true
            }
            R.id.menu_settings -> {
                viewState.showError("Not implemented", "Not implemented")
                false
            }
            R.id.menu_logout -> {
                AuthUI.getInstance().signOut(context)
                router.newRootScreen(LoginScreen())
                true
            }
            else -> false
        }

    }


}