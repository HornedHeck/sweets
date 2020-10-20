package com.hornedheck.echos.data.models

data class User(
    var token: String,
    val name: String,
    val email: String,
    val link: String
)