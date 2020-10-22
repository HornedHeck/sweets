package com.hornedheck.echos.data.models

data class UserEntity(
    var token: String = "",
    var name: String = "",
    var email: String = "",
    var link: String = ""
)

internal fun UserEntity.toUser() = com.hornedheck.echos.domain.models.User(token, name, email, link)

internal fun com.hornedheck.echos.domain.models.User.toEntity() = UserEntity(token, name, email, link)