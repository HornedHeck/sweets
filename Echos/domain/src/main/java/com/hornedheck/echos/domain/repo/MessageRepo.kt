package com.hornedheck.echos.domain.repo

import com.hornedheck.echos.domain.models.Message
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MessageRepo {

    fun observerMessages(channelId: String): Observable<Message>

    fun addMessage(channelId: String , message: Message): Completable

}