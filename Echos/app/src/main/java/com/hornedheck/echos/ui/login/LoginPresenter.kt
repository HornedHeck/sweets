package com.hornedheck.echos.ui.login

import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.data.models.User
import com.hornedheck.echos.data.repo.MessagesRepo
import com.hornedheck.echos.data.repo.UserRepo
import com.hornedheck.echos.navigation.ContactsScreen
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val router: Router,
    private val repo: UserRepo,
    private val messagesRepo: MessagesRepo
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
                        login(user)
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

    private fun login(user: User) {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)
            ?.addOnSuccessListener {
                user.token = it.token!!
                repo.login(user).subscribe(
                    {
                        messagesRepo.setToken(user.token)
                        router.newRootScreen(ContactsScreen())
                    },
                    { t ->
                        Timber.e(t)
                    }/*viewState::showError*/
                )
            }
    }

    fun onNameSelected(name: String) {
        repo.checkName(name).subscribe({
            if (it) {
                val email = FirebaseAuth.getInstance().currentUser!!.email!!
                val link = "@${name.replace(" ", "")}"
                login(User("", name, email, link))
            } else {
                viewState.wrongName();
            }
        }, viewState::showError)
    }


}