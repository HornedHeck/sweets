package com.hornedheck.echos.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseActivity
import com.hornedheck.echos.navigation.EchosNavigator
import com.hornedheck.echos.navigation.GlobalNavigation
import com.hornedheck.echos.utils.textInputDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    @Inject
    lateinit var navigation: GlobalNavigation

    private val navigator = EchosNavigator(this, R.id.flRootContainer)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(this)
            .getString("theme", "day")
            ?.let {
                when (it) {
                    "day" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    "night" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    "time" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME)
                    }
                }
            }
        presenter.startLogin()
    }

    override fun onResume() {
        super.onResume()
        navigation.navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigation.navHolder.removeNavigator()
    }

    override fun inject() {
        appComponent.inject(this)
    }

    override fun getName() {
        textInputDialog(
            presenter::onNameSelected,
            title = R.string.login_username_title
        ).show()
    }

    override fun wrongName() {
        textInputDialog(
            presenter::onNameSelected,
            title = R.string.login_username_title,
            message = R.string.login_username_error
        ).show()
    }

}