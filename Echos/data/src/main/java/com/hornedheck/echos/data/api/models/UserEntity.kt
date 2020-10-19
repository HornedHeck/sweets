package com.hornedheck.echos.data.api.models

import com.hornedheck.echos.data.models.User

data class UserEntity(
    var name: String = "",
    var link: String = ""
)

internal fun UserEntity.toUser() = User(
    name,
    link
)

internal fun User.toEntity() = UserEntity(name, link)