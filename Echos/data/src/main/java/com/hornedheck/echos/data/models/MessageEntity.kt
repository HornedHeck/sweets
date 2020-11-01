package com.hornedheck.echos.data.models

import com.hornedheck.echos.domain.models.Message
import java.time.Instant

data class MessageEntity(
    var fromId: String = "",
    var content: String = "",
    var time: Long = 0L,
    var isRead: Boolean = false
)

fun Message.toEntity(sender: String) = MessageEntity(sender, content, time.toEpochMilli())

fun MessageEntity.toMessage(me: String) = Message(me != fromId, content, Instant.ofEpochMilli(time))