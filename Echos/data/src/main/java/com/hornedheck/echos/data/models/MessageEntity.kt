package com.hornedheck.echos.data.models

data class MessageEntity(
    var fromId: String = "",
    var content: String = ""
)

fun com.hornedheck.echos.domain.models.Message.toEntity() = MessageEntity(fromId, content)

fun MessageEntity.toMessage() = com.hornedheck.echos.domain.models.Message(fromId, content)