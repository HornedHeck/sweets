package com.hornedheck.echos.data.models

import com.hornedheck.echos.domain.models.Message
import java.time.Instant

data class MessageEntity(
    var id: String = "",
    var fromId: String = "",
    var content: String = "",
    var time: Long = 0L,
    var isRead: Boolean = false
)

fun Message.toEntity(sender: String) = MessageEntity(
    fromId = sender,
    content = content,
    time = time.toEpochMilli()
)

fun MessageEntity.toMessage(me: String) = Message(
    id = id,
    isIncoming = me != fromId,
    content = content,
    time = Instant.ofEpochMilli(time)
)