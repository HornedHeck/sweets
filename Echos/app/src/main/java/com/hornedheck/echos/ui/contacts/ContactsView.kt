package com.hornedheck.echos.ui.contacts

import com.hornedheck.echos.base.BaseView
import com.hornedheck.echos.data.models.ChannelInfo
import com.hornedheck.echos.data.models.User
import moxy.viewstate.strategy.alias.Skip

interface ContactsView : BaseView {

    @Skip
    fun addContact(info: ChannelInfo)

}