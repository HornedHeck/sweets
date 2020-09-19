package com.hornedheck.restfultimer.presentation.timer

import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.presentation.list.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerViewModel(private val id: Long, repository: Repository) :
    ListViewModel<TimerStep>(repository) {

    private lateinit var timer: Timer

    val titleState = State<Pair<String, Int>>()

    override suspend fun loadData(): Response<List<TimerStep>> {
        val response = repository
            .getTimer(id)
        if (response.isSuccessful) {
            onTimerLoaded(response.data!!)
        }
        return response.map { it.steps!! }
    }

    private fun onTimerLoaded(timer: Timer) {
        this.timer = timer
        titleState.postValue(timer.name to timer.color)
    }

    fun update() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                repository.updateTimer(timer)
            }
        }
    }

}