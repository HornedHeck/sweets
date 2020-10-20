package com.hornedheck.echos.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface BaseView : MvpView {


    @OneExecution
    fun showError(title: String, content: String)

    @OneExecution
    fun showError(e: Throwable)

}