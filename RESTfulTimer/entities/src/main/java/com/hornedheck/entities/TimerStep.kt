package com.hornedheck.entities

data class TimerStep(
    val id: Int,
    val name: String,
    val description: String?,
    val duration: Int,
    val type: Int,
)