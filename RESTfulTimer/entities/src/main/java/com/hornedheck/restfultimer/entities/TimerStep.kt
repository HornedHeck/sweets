package com.hornedheck.restfultimer.entities

data class TimerStep(
    val id: Int,
    val name: String,
    val description: String?,
    val duration: Int,
    val type: StepType,
)