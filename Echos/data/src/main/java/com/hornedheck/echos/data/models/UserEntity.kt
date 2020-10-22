package com.hornedheck.echos.data.models

import com.hornedheck.echos.domain.models.User

data class UserEntity(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var link: String = ""
)

internal fun UserEntity.toUser() = User(id, name, email, link)

internal fun User.toEntity() = UserEntity(id, name, email, link)