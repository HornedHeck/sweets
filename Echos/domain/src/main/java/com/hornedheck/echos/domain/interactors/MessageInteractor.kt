package com.hornedheck.echos.domain.interactors

import com.hornedheck.echos.domain.models.Message
import com.hornedheck.echos.domain.repo.MessageRepo

class MessageInteractor(
    private val messageRepo: MessageRepo,
) {

    fun observeMessages(channelId: String) = messageRepo.observerMessages(channelId)

    fun sendMessage(channelId: String, message: Message) =
        messageRepo.addMessage(channelId, message)
}