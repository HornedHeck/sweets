package com.hornedheck.echos.ui.messages

import com.hornedheck.echos.base.list.ListView
import com.hornedheck.echos.domain.models.Message
import moxy.viewstate.strategy.alias.AddToEnd


interface MessagesView : ListView<Message> {

    @AddToEnd
    fun updateItem(message: Message)

    @AddToEnd
    fun deleteItem(message: Message)

}