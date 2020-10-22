package com.hornedheck.echos.ui.contacts

import com.hornedheck.echos.base.BaseView
import com.hornedheck.echos.domain.models.ChannelInfo
import com.hornedheck.echos.domain.models.User
import moxy.viewstate.strategy.alias.AddToEnd

interface ContactsView : BaseView {

    @AddToEnd
    fun addContact(info: com.hornedheck.echos.domain.models.ChannelInfo)

}