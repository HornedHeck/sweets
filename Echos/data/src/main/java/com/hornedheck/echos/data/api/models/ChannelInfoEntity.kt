package com.hornedheck.echos.data.api.models

import com.hornedheck.echos.data.models.ChannelInfo
import com.hornedheck.echos.data.models.User

data class ChannelInfoEntity(
    var id: String = "",
    var u1: String = "",
    var u2: String = ""
)

fun ChannelInfoEntity.toInfo(user: User) = ChannelInfo(id, user)