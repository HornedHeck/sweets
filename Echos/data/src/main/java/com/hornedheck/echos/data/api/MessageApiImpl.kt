package com.hornedheck.echos.data.api

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.models.MessageEntity
import com.hornedheck.firerx3.getObservableValues
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MessageApiImpl : MessageApi {

    private val db = Firebase.database.reference

    override fun observeMessage(channelId: String): Observable<MessageEntity> =
        db.child(CHANNELS).child(channelId).getObservableValues(MessageEntity::class)

    override fun sendMessage(channelId: String, entity: MessageEntity): Completable {
        return Completable.create {
            db.child(CHANNELS).child(channelId).push().setValue(entity)
            it.onComplete()
        }
    }
}