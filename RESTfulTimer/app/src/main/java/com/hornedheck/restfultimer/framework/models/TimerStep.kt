package com.hornedheck.restfultimer.framework.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hornedheck.restfultimer.entities.TimerStep

@Entity
data class TimerStep(
    val name: String,
    val description: String?,
    val duration: Int,
    val type: String,
    var timerId : Long
) {

    @PrimaryKey(autoGenerate = true)
    var stepId: Long? = null

    fun convert() = TimerStep(stepId!!, name, description, duration, type)
}