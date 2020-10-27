package com.hornedheck.echos.ui.messages

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.interactors.MessageInteractor
import com.hornedheck.echos.domain.models.Message
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.time.Instant
import javax.inject.Inject

class MessagesPresenter @Inject constructor(
    private val interactor: MessageInteractor,
) : BasePresenter<MessagesView>() {

    private val disposable = CompositeDisposable()

    private lateinit var channelId: String

    fun init(channelId: String) {
        disposable.add(interactor.observeMessages(channelId).subscribe(
            viewState::addItem, viewState::showError
        ))
    }

    fun sendMessage(content: String) {
        interactor.sendMessage(channelId, Message(false, content, Instant.now())).subscribe()
    }

}