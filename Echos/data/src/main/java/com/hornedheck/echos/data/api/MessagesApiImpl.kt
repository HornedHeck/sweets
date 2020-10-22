package com.hornedheck.echos.data.api

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.models.ChannelInfoEntity
import com.hornedheck.firerx3.getObservableValues
import com.hornedheck.firerx3.getSingleValue
import com.hornedheck.firerx3.observe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

internal class MessagesApiImpl : MessagesApi {

    private val db = Firebase.database.reference

    override fun getUserChannels(id: String): Observable<String> =
        db.child(USERS).child(id).child(CHANNELS).getObservableValues(String::class)

    override fun getChannelInfo(channelId: String): Single<ChannelInfoEntity> =
        db.child(CHANNELS).child(channelId).getSingleValue(ChannelInfoEntity::class)

    override fun addChannel(u1: String, u2: String): Single<String> {
        val push = db.child(CHANNELS).push()
        val channelId = push.key!!
        val info = ChannelInfoEntity(id = channelId, u1 = u1, u2 = u2)
        push.setValue(info)
        db.child(USERS).child(info.u1).child(CHANNELS).push().setValue(channelId)
        db.child(USERS).child(info.u2).child(CHANNELS).push().setValue(channelId)
        return Single.just(channelId)
    }

//    override fun getMessages(channelId: String): Observable<MessageEntity> =
//        db.child(CHANNELS).child(channelId).child(MESSAGES)
//            .getObservableValues(MessageEntity::class)

    override fun observeChannels(id: String): Observable<String> =
        db.child(USERS).child(id).child(CHANNELS).observe(String::class)

//    override fun checkChannel(user1: String, user2: String): Single<Boolean> =
//        toUserId(user2).flatMap { id ->
//            getUserChannels(user1)
//                .flatMapSingle { getChannelInfo(it) }
//                .all { it.u1 != id && it.u2 != id }
//        }


}