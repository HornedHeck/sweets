package com.hornedheck.echos.data.api

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.MessageEntity
import com.hornedheck.echos.data.api.models.UserEntity
import com.hornedheck.firerx3.getObservableValues
import com.hornedheck.firerx3.getObservableValuesWithKey
import com.hornedheck.firerx3.getSingleValue
import com.hornedheck.firerx3.observe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

internal class MessagesApiImpl : MessagesApi {

    private val db = Firebase.database.reference

    private fun toUserId(token: String): Single<String> =
        db.child(USERS).getObservableValuesWithKey(UserEntity::class)
            .filter { (_, user) -> user.token == token }
            .firstOrError()
            .map { (key, _) -> key }

    override fun getUserChannels(token: String): Observable<String> =
        toUserId(token).flatMapObservable {
            db.child(USERS).child(it).child(CHANNELS).getObservableValues(String::class)
        }

    override fun getChannelInfo(channelId: String): Single<ChannelInfoEntity> =
        db.child(CHANNELS).child(channelId).getSingleValue(ChannelInfoEntity::class)

    override fun addChannel(u1: String, u2: String): Single<String> =
        toUserId(u1).flatMap { first -> toUserId(u2).map { second -> first to second } }!!
            .flatMap { (first, second) ->
                val push = db.child(CHANNELS).push()
                val channelId = push.key!!
                val info = ChannelInfoEntity(id = channelId , u1 = first, u2 = second)
                push.setValue(info)
                db.child(USERS).child(info.u1).child(CHANNELS).push().setValue(channelId)
                db.child(USERS).child(info.u2).child(CHANNELS).push().setValue(channelId)
                Single.just(channelId)
            }

    override fun getMessages(channelId: String): Observable<MessageEntity> =
        db.child(CHANNELS).child(channelId).child(MESSAGES)
            .getObservableValues(MessageEntity::class)

    override fun observeChannels(token: String): Observable<String> =
        toUserId(token).flatMapObservable {
            db.child(USERS).child(it).child(CHANNELS).observe(String::class)
        }

    override fun checkChannel(user1: String, user2: String): Single<Boolean> =
        toUserId(user2).flatMap { id ->
            getUserChannels(user1)
                .flatMapSingle { getChannelInfo(it) }
                .all { it.u1 != id && it.u2 != id }
        }


}