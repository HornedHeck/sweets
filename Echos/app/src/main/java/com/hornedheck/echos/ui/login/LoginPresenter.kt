package com.hornedheck.echos.ui.login

import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.navigation.ContactsScreen
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<LoginView>() {

    init {
        viewState.startLogin()
    }

    fun onLoginResult(resultCode: Int, result: IdpResponse?) {
        if (resultCode != Activity.RESULT_OK) {
            result?.error?.localizedMessage?.let {
                viewState.showError("Authentication error", it)
            }
        } else {
            router.newRootScreen(ContactsScreen())
        }
    }


}