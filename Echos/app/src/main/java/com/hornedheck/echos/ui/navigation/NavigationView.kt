package com.hornedheck.echos.ui.navigation

import com.hornedheck.echos.base.BaseView
import com.hornedheck.echos.domain.models.User
import moxy.viewstate.strategy.alias.AddToEndSingle

interface NavigationView : BaseView{

    @AddToEndSingle
    fun setUserInfo(user: User)

}