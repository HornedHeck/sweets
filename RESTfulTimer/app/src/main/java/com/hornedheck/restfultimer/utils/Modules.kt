package com.hornedheck.restfultimer.utils

import com.hornedheck.restfultimer.data.LocalApi
import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.framework.local.room.RoomLocal
import com.hornedheck.restfultimer.presentation.timer.TimerViewModel
import com.hornedheck.restfultimer.presentation.timers.TimersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<LocalApi> { RoomLocal(get()) }
    single { Repository(get()) }
}

val viewModelsModule = module {
    viewModel { TimersViewModel(get()) }
    viewModel { (id: Long) -> TimerViewModel(id, get()) }
}