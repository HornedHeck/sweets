package com.hornedheck.restfultimer.presentation.list

import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.utils.BaseViewModel
import kotlinx.coroutines.*

abstract class ListViewModel<T>(protected val repository: Repository) : BaseViewModel() {

    val items = DisplayableState<List<T>>()
    val isLoading = State(true)
    val isError = State(false)

    fun updateData() {
        CoroutineScope(Dispatchers.Main).launch {
            isLoading.setValue(true)
            val response = withContext(Dispatchers.IO) {
                delay(100)
                loadData()
            }
            isLoading.setValue(false)
            if (response.isSuccessful && !response.data.isNullOrEmpty()) {
                items.show(response.data!!)
            } else {
                isError.setValue(true)
            }
        }
    }

    protected abstract suspend fun loadData(): Response<List<T>>

}