package com.hornedheck.restfultimer.framework.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimerRaw(
    val name: String,
    val color: Int,
    val duration: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var timerId: Long? = null
}