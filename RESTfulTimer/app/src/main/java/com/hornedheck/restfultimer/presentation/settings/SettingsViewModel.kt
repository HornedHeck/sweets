package com.hornedheck.restfultimer.presentation.settings

import androidx.lifecycle.ViewModel
import com.hornedheck.restfultimer.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: Repository) : ViewModel() {

    fun clearData() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.clearData()
        }
    }

}