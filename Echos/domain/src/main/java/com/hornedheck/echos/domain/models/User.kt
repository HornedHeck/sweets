package com.hornedheck.echos.domain.models

data class User(
    var token: String,
    val name: String,
    val email: String,
    val link: String
)