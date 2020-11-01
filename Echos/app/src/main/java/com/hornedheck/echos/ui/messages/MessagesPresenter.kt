package com.hornedheck.echos.ui.messages

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.interactors.MessageInteractor
import com.hornedheck.echos.domain.models.Message
import com.hornedheck.echos.navigation.GlobalNavigation
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.time.Instant
import javax.inject.Inject

class MessagesPresenter @Inject constructor(
    private val interactor: MessageInteractor,
    private val navigation: GlobalNavigation
) : BasePresenter<MessagesView>() {

    private val disposable = CompositeDisposable()

    private lateinit var channelId: String

    fun init(channelId: String) {
        disposable.add(
            interactor.observeMessages(channelId).subscribe(
                viewState::addItem, viewState::showError
            )
        )
        this.channelId = channelId
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun sendMessage(content: String) {
        if (content.isBlank()) return
        interactor.sendMessage(channelId, Message(false, content, Instant.now())).subscribe()
    }

    fun back() {
        navigation.router.exit()
    }

}