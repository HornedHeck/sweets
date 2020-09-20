package com.hornedheck.restfultimer.data

import com.hornedheck.restfultimer.entities.Timer

class Repository(private val local: LocalApi) {

    suspend fun createTimer() = local.createTimer()

    suspend fun getTimers() = local.getTimers()

    suspend fun updateTimer(timer: Timer) = local.updateTimer(timer)

    suspend fun deleteTimer(id: Long) = local.deleteTimer(id)

    suspend fun getTimer(id: Long) = local.getTimer(id)

}