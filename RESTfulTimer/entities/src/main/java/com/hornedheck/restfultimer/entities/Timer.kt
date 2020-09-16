package com.hornedheck.restfultimer.entities

class Timer(
    val id: Int,
    val name: String,
    val color: Int,
    val duration: Int,
    val steps: List<TimerStep>? = null
)