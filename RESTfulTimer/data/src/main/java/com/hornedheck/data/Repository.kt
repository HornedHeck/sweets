package com.hornedheck.data

import com.hornedheck.entities.Timer

class Repository(private val local: LocalApi) {

    suspend fun createTimer(timer: Timer) = local.createTimer(timer)

    suspend fun getTimers() = local.getTimers()

    suspend fun updateTimer(timer: Timer) = local.updateTimer(timer)

    suspend fun deleteTimer(id: Int) = local.deleteTimer(id)

}