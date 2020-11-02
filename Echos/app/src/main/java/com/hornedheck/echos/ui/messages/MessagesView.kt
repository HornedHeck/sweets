package com.hornedheck.echos.ui.messages

import com.hornedheck.echos.base.list.ListView
import com.hornedheck.echos.domain.models.Message
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.OneExecution


interface MessagesView : ListView<Message> {

    @AddToEnd
    fun updateItem(message: Message)

    @AddToEnd
    fun deleteItem(message: Message)

    @OneExecution
    fun showSendControls()

    @OneExecution
    fun showEditControls()

    @OneExecution
    fun editMessage(message: Message)

}