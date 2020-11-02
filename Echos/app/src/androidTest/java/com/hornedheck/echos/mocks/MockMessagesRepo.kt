package com.hornedheck.echos.mocks

import com.hornedheck.echos.domain.models.Action
import com.hornedheck.echos.domain.models.ActionType
import com.hornedheck.echos.domain.models.Message
import com.hornedheck.echos.domain.repo.MessageRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class MockMessagesRepo : MessageRepo {

    private val messages = PublishSubject.create<Action<Message>>()

    override fun observerMessages(channelId: String, me: String): Observable<Action<Message>> {
        return messages
    }

    override fun addMessage(channelId: String, me: String, message: Message): Completable {
        messages.onNext(
            Action(message, ActionType.INSERT)
        )
        return Completable.complete()
    }

    override fun deleteMessage(channelId: String, me: String, message: Message): Completable {
        messages.onNext(
            Action(message, ActionType.DELETE)
        )
        return Completable.complete()
    }

    override fun updateMessage(channelId: String, me: String, message: Message): Completable {
        messages.onNext(
            Action(message, ActionType.UPDATE)
        )
        return Completable.complete()
    }
}