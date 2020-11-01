package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.models.MessageEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MessageApi {

    fun observeMessage(channelId: String): Observable<MessageEntity>

    fun sendMessage(channelId: String, entity: MessageEntity): Completable

    fun readMessages(channelId: String, me: String)

}