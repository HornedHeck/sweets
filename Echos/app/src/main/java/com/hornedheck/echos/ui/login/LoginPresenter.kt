package com.hornedheck.echos.ui.login

import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.data.models.User
import com.hornedheck.echos.data.repo.MessagesRepo
import com.hornedheck.echos.navigation.ContactsScreen
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val router: Router,
    private val repo: MessagesRepo
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
            FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnSuccessListener {
                val firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val name = firebaseUser.displayName!!
                val link = "@${name.replace(" ", "")}"
                val user = User(it.token!!, name, firebaseUser.email!!, link)
                repo.login(user).subscribe(
                    { router.newRootScreen(ContactsScreen()) },
                    { e -> viewState.showError("Auth error", e.message!!) })
            }
        }
    }


}