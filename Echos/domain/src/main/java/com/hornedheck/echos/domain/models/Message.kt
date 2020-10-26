package com.hornedheck.echos.domain.models

import java.time.Instant

data class Message(
    val isIncoming : Boolean,
    val content : String,
    val time : Instant
)