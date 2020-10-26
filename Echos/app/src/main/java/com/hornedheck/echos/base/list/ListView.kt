package com.hornedheck.echos.base.list

import com.hornedheck.echos.base.BaseView
import moxy.viewstate.strategy.alias.AddToEnd

interface ListView<T> : BaseView {

    @AddToEnd
    fun addItem(item: T)

}