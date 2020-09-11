package com.hornedheck.data

import com.hornedheck.entities.Response
import com.hornedheck.entities.Timer

interface LocalApi {

    suspend fun createTimer(timer: Timer): Response<Unit>

    suspend fun getTimers(): Response<List<Timer>>

    suspend fun updateTimer(timer: Timer): Response<Unit>

    suspend fun deleteTimer(id: Int): Response<Unit>

}