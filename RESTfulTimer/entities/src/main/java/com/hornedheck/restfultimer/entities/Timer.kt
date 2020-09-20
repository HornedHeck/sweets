package com.hornedheck.restfultimer.entities

class Timer(
    val id: Long,
    var name: String,
    var color: Int,
    var duration: Int,
    val steps: MutableList<TimerStep>? = null
)