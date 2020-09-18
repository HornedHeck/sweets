package com.hornedheck.restfultimer.entities

data class TimerStep(
    val id: Int,
    val name: String,
    var description: String?,
    var duration: Int,
    val type: String,
)