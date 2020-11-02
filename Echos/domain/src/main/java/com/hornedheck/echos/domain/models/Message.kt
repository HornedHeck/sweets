package com.hornedheck.echos.domain.models

import java.time.Instant

data class Message(
    val id: String,
    val isIncoming: Boolean,
    var content: String,
    val time: Instant,
) {

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false

        return true
    }

}