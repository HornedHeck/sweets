package com.hornedheck.restfultimer.entities

data class TimerStep(
    val id: Long?,
    val name: String,
    var description: String?,
    var duration: Int,
    val type: Int,
    var position: Int = 0
)