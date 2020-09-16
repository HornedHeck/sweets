package com.hornedheck.restfultimer.framework.local

import android.graphics.Color
import com.hornedheck.restfultimer.data.LocalApi
import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.entities.Response.Companion.Success
import com.hornedheck.restfultimer.entities.Timer

class MockLocal : LocalApi {

    override suspend fun createTimer(timer: Timer): Response<Unit> {
        return Success
    }

    override suspend fun getTimers(): Response<List<Timer>> {
        return Response(
            listOf(
                Timer(0, "Name 1", Color.RED, 10),
                Timer(1, "Name 2", Color.GREEN, 20),
                Timer(2, "Name 3", Color.BLUE, 511),
                Timer(3, "Name 4", Color.WHITE, 12541),
            )
        )
    }

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
                Color.GREEN,
                100,
                listOf()
            )
        )
    }
}