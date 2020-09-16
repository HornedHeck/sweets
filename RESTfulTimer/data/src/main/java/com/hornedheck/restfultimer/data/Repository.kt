package com.hornedheck.restfultimer.data

import com.hornedheck.restfultimer.entities.Timer

class Repository(private val local: LocalApi) {

    suspend fun createTimer(timer: Timer) = local.createTimer(timer)

    suspend fun getTimers() = local.getTimers()

    suspend fun updateTimer(timer: Timer) = local.updateTimer(timer)

    suspend fun deleteTimer(id: Int) = local.deleteTimer(id)

    suspend fun getTimer(id: Int) = local.getTimer(id)

}