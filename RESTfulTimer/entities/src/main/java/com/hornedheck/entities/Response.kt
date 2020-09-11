package com.hornedheck.entities

data class Response<T>(
    val data: T?,
    val isSuccessful: Boolean = data == null
)