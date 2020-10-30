package com.hornedheck.echos.ui.navigation

import com.hornedheck.echos.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NavigationPresenter @Inject constructor(
    private val router: Router,
) : BasePresenter<NavigationView>() {


}