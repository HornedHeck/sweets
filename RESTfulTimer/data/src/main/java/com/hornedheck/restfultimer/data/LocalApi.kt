package com.hornedheck.restfultimer.data

import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.entities.Timer

interface LocalApi {

    suspend fun createTimer(timer: Timer): Response<Unit>

    suspend fun getTimers(): Response<List<Timer>>

    suspend fun updateTimer(timer: Timer): Response<Unit>

    suspend fun deleteTimer(id: Int): Response<Unit>

    suspend fun getTimer(id: Int) : Response<Timer>

}