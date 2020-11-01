package com.hornedheck.echos.ui.login

import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.models.User
import com.hornedheck.echos.domain.repo.UserRepo
import com.hornedheck.echos.navigation.GlobalNavigation
import com.hornedheck.echos.navigation.MainScreen
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val navigation: GlobalNavigation,
    private val repo: UserRepo,
) : BasePresenter<LoginView>() {

    init {
        startLogin()
    }

    fun startLogin() = viewState.startLogin()


    fun onLoginResult(resultCode: Int, response: IdpResponse?) {
        if (resultCode != Activity.RESULT_OK) {
            response?.error?.localizedMessage?.let {
                viewState.showError("Authentication error", it)
            }
        } else {
            FirebaseAuth.getInstance().currentUser?.email?.let { email ->
                repo.isRegistered(email).subscribe(
                    { user ->
//                      User is registered
                        login(user, false)
                    },
                    viewState::showError,
                    {
//                      Register new user
                        viewState.getName()
                    }
                )
            } ?: run {
                // TODO error here
            }
        }
    }

    private fun login(user: User, new: Boolean) {
        repo.login(user, new).subscribe(
            { navigation.router.newRootScreen(MainScreen()) },
            viewState::showError
        )
    }

    fun onNameSelected(name: String) {
        repo.checkName(name).subscribe({
            if (it) {
                val email = FirebaseAuth.getInstance().currentUser!!.email!!
                val link = "@${name.replace(" ", "")}"
                login(User("", name, email, link), true)
            } else {
                viewState.wrongName()
            }
        }, viewState::showError)
    }


}