package com.hornedheck.echos.data.api.models

import com.hornedheck.echos.data.models.Message

data class MessageEntity(
    var fromId: String = "",
    var content: String = ""
)

fun Message.toEntity() = MessageEntity(fromId, content)

fun MessageEntity.toMessage() = Message(fromId, content)