package com.hornedheck.echos.data.models

import com.hornedheck.echos.domain.models.ChannelInfoPartial

data class ChannelInfoEntity(
    var id: String = "",
    var u1: String = "",
    var u2: String = ""
)

fun ChannelInfoEntity.toInfo() = ChannelInfoPartial(id, u1 , u2)