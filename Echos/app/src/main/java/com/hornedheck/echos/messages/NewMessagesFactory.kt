package com.hornedheck.echos.messages

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.hornedheck.echos.domain.interactors.ChannelsInteractor

class NewMessagesFactory(private val interactor: ChannelsInteractor) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            NewMessagesWorker::class.java.name ->
                NewMessagesWorker(appContext, workerParameters, interactor)
            else ->
                null
        }
    }
}