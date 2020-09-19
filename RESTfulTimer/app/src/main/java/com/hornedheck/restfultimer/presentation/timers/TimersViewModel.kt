package com.hornedheck.restfultimer.presentation.timers

import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.presentation.list.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimersViewModel(repository: Repository) : ListViewModel<Timer>(repository) {

    override suspend fun loadData() = repository.getTimers()

    fun addClicked() {
        CoroutineScope(Dispatchers.Main).launch {
            val createRes = withContext(Dispatchers.IO) {
                repository.createTimer()
            }
            if (createRes.isSuccessful) {
                updateData()
            }
        }
    }

    fun deleteClicked(id: Long, data: List<Timer>) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.IO) {
                repository.deleteTimer(id)
            }
            if (response.isSuccessful) {
                data.filter { it.id != id }.let {
                    if (it.isNotEmpty()) {
                        items.show(it)
                    } else {
                        items.hide()
                        isError.postValue(true)
                    }
                }
            }
        }
    }
}