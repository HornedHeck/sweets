package com.hornedheck.echos.domain.models

enum class ActionType {
    INSERT, UPDATE, DELETE
}

data class Action<T>(val data: T, val type: ActionType)