package com.hornedheck.echos.ui.login

import com.hornedheck.echos.di.ScreenScope
import com.hornedheck.echos.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.terrakok.cicerone.Router

@Module
class LoginModule {

    @ScreenScope
    @Provides
    fun presenter(router: Router, userRepo: UserRepo) = LoginPresenter(router, userRepo)
}

@ScreenScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    fun inject(to: LoginFragment)

}