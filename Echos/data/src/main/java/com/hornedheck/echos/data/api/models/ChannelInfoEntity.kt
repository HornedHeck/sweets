package com.hornedheck.echos.data.api.models

import com.hornedheck.echos.data.models.ChannelInfo

data class ChannelInfoEntity(
    var id: String = "",
    var name: String = "",
    var link: String = ""
)

fun ChannelInfoEntity.toInfo() = ChannelInfo(id, name, link)