package com.hornedheck.restfultimer.framework.local.room

import androidx.room.*
import com.hornedheck.restfultimer.framework.models.Timer
import com.hornedheck.restfultimer.framework.models.TimerRaw
import com.hornedheck.restfultimer.framework.models.TimerStep

@Dao
interface TimerDao {

    @Transaction
    fun insertTimer(timer: Timer) {
        val id = insertTimer(timer.timer)
        timer.steps.forEach { it.timerId = id }
        insertTimerSteps(timer.steps)
    }

    @Insert
    fun insertTimer(timer: TimerRaw): Long

    @Insert
    fun insertTimerStep(timerStep: TimerStep)


    @Insert
    fun insertTimerSteps(timerSteps: List<TimerStep>)


    @Update
    fun updateTimer(timer: TimerRaw)

    @Update
    fun updateTimerStep(timerStep: TimerStep)

    @Query("DELETE FROM TimerRaw WHERE timerId = :id")
    fun deleteTimer(id: Long)

    @Query("DELETE FROM TimerStep WHERE stepId = :id")
    fun deleteTimerStep(id: Long)

    @Transaction
    @Query("SELECT * FROM TimerRaw")
    fun getTimers(): List<Timer>

    @Transaction
    @Query("SELECT * FROM TimerRaw WHERE timerId = :id")
    fun getTimer(id: Long): Timer

}