package com.hornedheck.echos.ui.navigation

import com.hornedheck.echos.R
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.repo.UserRepo
import com.hornedheck.echos.navigation.ContactsScreen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NavigationPresenter @Inject constructor(
    private val router: Router,
    userRepo: UserRepo,
) : BasePresenter<NavigationView>() {

    private var lastNav = 0

    init {
        userRepo.getUser().subscribe(viewState::setUserInfo, viewState::showError)
    }

    fun navigate(to: Int): Boolean {
        if (to == lastNav) return true
        return when (to) {
            R.id.menu_contacts -> {
                router.replaceScreen(ContactsScreen())
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