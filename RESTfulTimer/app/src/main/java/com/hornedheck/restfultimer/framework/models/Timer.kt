package com.hornedheck.restfultimer.framework.models

import androidx.room.Embedded
import androidx.room.Relation
import com.hornedheck.restfultimer.entities.Timer

data class Timer(
    @Embedded
    val timer: TimerRaw,
    @Relation(
        parentColumn = "timerId",
        entityColumn = "timerId"
    )
    val steps: List<TimerStep>
) {

    fun convert() = Timer(
        timer.timerId!!,
        timer.name,
        timer.color,
        timer.duration,
        steps.map(TimerStep::convert).sortedBy { it.position }.toMutableList()
    )


}