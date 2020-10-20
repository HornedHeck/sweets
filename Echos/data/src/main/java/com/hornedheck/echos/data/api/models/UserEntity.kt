package com.hornedheck.echos.data.api.models

import com.hornedheck.echos.data.models.User

data class UserEntity(
    var token: String = "",
    var name: String = "",
    var email: String = "",
    var link: String = ""
)

internal fun UserEntity.toUser() = User(name, link, token, email)

internal fun User.toEntity() = UserEntity(token, name, email, link)