package com.hornedheck.restfultimer.data

import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.entities.Timer

interface LocalApi {

    suspend fun createTimer(): Response<Unit>

    suspend fun getTimers(): Response<List<Timer>>

    suspend fun updateTimer(timer: Timer): Response<Unit>

    suspend fun deleteTimer(id: Long): Response<Unit>

    suspend fun getTimer(id: Long) : Response<Timer>

}