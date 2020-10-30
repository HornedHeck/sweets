package com.hornedheck.echos.ui.login

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseFragment
import com.hornedheck.echos.utils.textInputDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    companion object {
        private const val RC_SIGN_IN = 10
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            presenter.onLoginResult(resultCode, IdpResponse.fromResultIntent(data))
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun startLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun inject() {
        globalFlowComponent.loginComponent().inject(this)
    }

    override fun getName() {
        requireContext().textInputDialog(
            presenter::onNameSelected,
            title = R.string.login_username_title
        ).show()
    }

    override fun wrongName() {
        requireContext().textInputDialog(
            presenter::onNameSelected,
            title = R.string.login_username_title,
            message = R.string.login_username_error
        ).show()
    }
}