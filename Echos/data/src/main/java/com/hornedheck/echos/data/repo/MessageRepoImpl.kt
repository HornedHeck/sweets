package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessageApi
import com.hornedheck.echos.data.models.toEntity
import com.hornedheck.echos.data.models.toMessage
import com.hornedheck.echos.domain.models.Message
import com.hornedheck.echos.domain.repo.MessageRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

internal class MessageRepoImpl(private val messageApi: MessageApi) : MessageRepo {

    override fun observerMessages(channelId: String): Observable<Message> =
        messageApi.observeMessage(channelId)
            .map { it.toMessage() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addMessage(channelId: String, message: Message): Completable {
        return messageApi.sendMessage(channelId, message.toEntity())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}