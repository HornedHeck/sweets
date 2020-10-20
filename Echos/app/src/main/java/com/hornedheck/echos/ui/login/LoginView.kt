package com.hornedheck.echos.ui.login

import com.hornedheck.echos.base.BaseView
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.SingleState

interface LoginView : BaseView {

    @SingleState
    fun startLogin()

    @OneExecution
    fun getName()

    @OneExecution
    fun wrongName()

}