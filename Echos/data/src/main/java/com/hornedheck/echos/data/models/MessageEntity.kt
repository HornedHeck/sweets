package com.hornedheck.echos.data.models

import com.hornedheck.echos.domain.models.Message
import java.time.Instant

data class MessageEntity(
    var fromId: String = "",
    var content: String = "",
    var time : Long = 0L
)

fun Message.toEntity() = MessageEntity(fromId, content , time.toEpochMilli())

fun MessageEntity.toMessage() = Message(fromId, content , Instant.ofEpochMilli(time))