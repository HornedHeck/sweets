package com.hornedheck.restfultimer.framework.local.room

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import com.hornedheck.restfultimer.data.LocalApi
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.entities.handle
import com.hornedheck.restfultimer.entities.handleUnit
import com.hornedheck.restfultimer.framework.models.StepType
import com.hornedheck.restfultimer.framework.models.TimerRaw
import com.hornedheck.restfultimer.framework.models.Timer as LTimer
import com.hornedheck.restfultimer.framework.models.TimerStep as LTimerStep

class RoomLocal(context: Context) : LocalApi {
    private val db = Room.databaseBuilder(context, TimerDatabase::class.java, "Timer")
        .fallbackToDestructiveMigration()
        .build()

    override suspend fun createTimer() = handleUnit {
        db.timerDao.insertTimer(getDefaultTimer())
    }

    override suspend fun getTimers() = handle {
        db.timerDao.getTimers().map(LTimer::convert)
    }

    override suspend fun updateTimer(timer: Timer) = handleUnit {
        val converted = timer.convert()
        db.timerDao.updateTimer(converted.timer)
        converted.steps.forEach(db.timerDao::updateTimerStep)
    }

    override suspend fun deleteTimer(id: Long) = handleUnit {
        db.timerDao.deleteTimer(id)
    }

    override suspend fun getTimer(id: Long) = handle {
        db.timerDao.getTimer(id).convert()
    }

    private fun TimerStep.convert(parent: Long) =
        LTimerStep(
            name,
            description,
            duration,
            type,
            parent
        ).also { it.stepId = id }

    private fun Timer.convert() =
        LTimer(
            TimerRaw(
                name,
                color,
                duration
            ),
            steps?.map { it.convert(id) } ?: emptyList())

    private fun getDefaultTimer() = LTimer(
        TimerRaw("New Timer", Color.WHITE, 100),
        getDefaultTimerSteps()
    )

    private fun getDefaultTimerSteps(): List<LTimerStep> {
        return listOf(
            LTimerStep("Prepare", null, 10, StepType.PREPARE.name, 0),
            LTimerStep("Work", null, 40, StepType.WORK.name, 0),
            LTimerStep("Repeats", null, 2, StepType.REPEAT.name, 0),
            LTimerStep("Sets", null, 1, StepType.SETS.name, 0),
        )
    }


}