package com.hornedheck.echos.ui.messages

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.interactors.MessageInteractor
import com.hornedheck.echos.domain.models.Action
import com.hornedheck.echos.domain.models.ActionType
import com.hornedheck.echos.domain.models.Message
import com.hornedheck.echos.navigation.GlobalNavigation
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.time.Instant
import javax.inject.Inject

class MessagesPresenter @Inject constructor(
    private val interactor: MessageInteractor,
    private val navigation: GlobalNavigation,
) : BasePresenter<MessagesView>() {

    private val disposable = CompositeDisposable()

    private lateinit var channelId: String

    fun init(channelId: String) {
        disposable.add(
            interactor.observeMessages(channelId).subscribe(
                this::proceedItem, viewState::showError
            )
        )
        this.channelId = channelId
    }

    private fun proceedItem(action: Action<Message>) {
        when (action.type) {
            ActionType.INSERT -> viewState.addItem(action.data)
            ActionType.UPDATE -> viewState.updateItem(action.data)
            ActionType.DELETE -> viewState.deleteItem(action.data)
        }
    }

    fun deleteMessage(message: Message) {
        interactor.deleteMessage(message, channelId).subscribe({}, viewState::showError)
    }

    fun prepareUpdate(message: Message) {
        viewState.showEditControls()
        viewState.editMessage(message)
    }

    fun cancelUpdate() {
        viewState.showSendControls()
    }

    fun updateMessage(message: Message) {
        if (message.content.isBlank()) {
            interactor.deleteMessage(message, channelId).subscribe({}, viewState::showError)
        } else {
            interactor.updateMessage(message, channelId).subscribe({}, viewState::showError)
        }
        viewState.showSendControls()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun sendMessage(content: String) {
        if (content.isBlank()) return
        interactor.sendMessage(channelId, Message("", false, content.trim(), Instant.now()))
            .subscribe()
    }

    fun back() {
        navigation.router.exit()
    }

}