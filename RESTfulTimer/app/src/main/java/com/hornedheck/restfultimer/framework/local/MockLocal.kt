package com.hornedheck.restfultimer.framework.local

import android.graphics.Color
import com.hornedheck.restfultimer.data.LocalApi
import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.entities.Response.Companion.Success
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.framework.models.StepType as EStepType

class MockLocal : LocalApi {

    private val timers = mutableListOf(
        Timer(0, "Name 1", Color.RED, 10),
        Timer(1, "Name 2", Color.GREEN, 20),
        Timer(2, "Name 3", Color.BLUE, 511),
        Timer(3, "Name 4", Color.WHITE, 12541),
    )

    override suspend fun createTimer(): Response<Unit> {
        timers.add(
            Timer(
                timers.size,
                "Name of ${timers.size}",
                Color.WHITE,
                100,
                steps
            )
        )
        return Success
    }

    override suspend fun getTimers(): Response<List<Timer>> {
        return Response(timers)
    }

    private val steps = listOf(
        TimerStep(1, "Prepare", null, 10, EStepType.PREPARE.name),
        TimerStep(1, "Work", null, 40, EStepType.WORK.name),
        TimerStep(1, "Repeats", null, 2, EStepType.REPEAT.name),
        TimerStep(1, "Sets", null, 1, EStepType.SETS.name),
    )

    override suspend fun updateTimer(timer: Timer): Response<Unit> {
        return Success
    }

    override suspend fun deleteTimer(id: Int): Response<Unit> {
        return Success
    }

    override suspend fun getTimer(id: Int): Response<Timer> {
        return Response(
            Timer(
                id,
                "Name of $id",
                Color.WHITE,
                100,
                steps
            )
        )
    }
}