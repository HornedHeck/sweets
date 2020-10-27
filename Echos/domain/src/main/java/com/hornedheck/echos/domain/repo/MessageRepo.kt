package com.hornedheck.echos.domain.repo

import com.hornedheck.echos.domain.models.Message
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MessageRepo {

    fun observerMessages(channelId: String , me : String): Observable<Message>

    fun addMessage(channelId: String , me : String , message: Message): Completable

}