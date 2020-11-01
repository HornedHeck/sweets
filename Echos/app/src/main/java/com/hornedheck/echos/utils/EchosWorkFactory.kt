package com.hornedheck.echos.utils

import androidx.work.DelegatingWorkerFactory
import com.hornedheck.echos.domain.interactors.ChannelsInteractor
import com.hornedheck.echos.messages.NewMessagesFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EchosWorkFactory @Inject constructor(
    interactor: ChannelsInteractor
) : DelegatingWorkerFactory() {

    init {
        addFactory(NewMessagesFactory(interactor))
    }

}