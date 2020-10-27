package com.hornedheck.echos.domain.interactors

import com.hornedheck.echos.domain.models.Message
import com.hornedheck.echos.domain.repo.MessageRepo
import com.hornedheck.echos.domain.repo.UserRepo

class MessageInteractor(
    private val messageRepo: MessageRepo,
    private val userRepo: UserRepo,
) {

    fun observeMessages(channelId: String) = messageRepo.observerMessages(channelId, userRepo.id)

    fun sendMessage(channelId: String, message: Message) =
        messageRepo.addMessage(channelId, userRepo.id, message)
}