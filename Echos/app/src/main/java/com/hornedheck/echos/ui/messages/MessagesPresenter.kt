package com.hornedheck.echos.ui.messages

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.interactors.MessageInteractor
import com.hornedheck.echos.domain.models.Message
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.time.Instant
import javax.inject.Inject

class MessagesPresenter @Inject constructor(
    private val channelId: String,
    private val interactor: MessageInteractor,
) : BasePresenter<MessagesView>() {

    private lateinit var me: String

    private val disposable = CompositeDisposable()

    init {
        disposable.add(interactor.observeMessages(channelId).subscribe(
            viewState::addItem, viewState::showError
        ))
    }

    fun sendMessage(content: String) {
        interactor.sendMessage(channelId, Message(me, content, Instant.now())).subscribe()
    }

}