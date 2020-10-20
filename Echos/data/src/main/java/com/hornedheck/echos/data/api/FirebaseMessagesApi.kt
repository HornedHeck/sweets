package com.hornedheck.echos.data.api

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.MessageEntity
import com.hornedheck.echos.data.api.models.UserEntity
import com.hornedheck.firerx3.getObservableValues
import com.hornedheck.firerx3.getSingleValue
import com.hornedheck.firerx3.observe
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

internal class FirebaseMessagesApi : MessagesApi {

    companion object {
        private const val MESSAGES = "messages"
        private const val CHANNELS = "channels"
        private const val USERS = "users"
        private const val TOKEN = "token"
        private const val TIMEOUT = 5L
    }

    private val db = Firebase.database.reference

    private fun toUserId(token: String): Single<String> =
        Single.create { emitter ->
            db.child(USERS).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) =
                    snapshot.children
                        .mapNotNull { it.key to it.getValue<UserEntity>() }
                        .firstOrNull { it.second?.token == token }
                        ?.let { emitter.onSuccess(it.first) }
                        ?: run {
                            emitter.onError(NullPointerException())
                        }

                override fun onCancelled(error: DatabaseError) =
                    emitter.onError(error.toException())
            })
        }

    override fun getUser(token: String): Single<UserEntity> =
        toUserId(token).flatMap {
            db.child(USERS).child(it).getSingleValue()
        }

    override fun loginUser(user: UserEntity): Completable =
        Completable.create { emitter ->
            db.child(USERS).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) =
                    snapshot.children
                        .mapNotNull { it.key to it.getValue<UserEntity>()?.email }
                        .firstOrNull { (key, email) -> email == user.email && key != null }
                        ?.let { (key, _) ->
                            db.child(USERS).child(key!!).child(TOKEN).setValue(user.token)
                            emitter.onComplete()
                        }
                        ?: run {
                            db.child(USERS).push().setValue(user)
                            emitter.onComplete()
                        }

                override fun onCancelled(error: DatabaseError) =
                    emitter.onError(error.toException())
            })
        }

    override fun findUser(link: String): Single<String> =
        Single.create { emitter ->
            db.child(USERS).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.map { it.key to it.getValue<UserEntity>() }
                        .firstOrNull { it.second?.link == link }
                        ?.let { emitter.onSuccess(it.first) }
                        ?: run { emitter.onError(NullPointerException()) }
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }
            })
        }

    override fun getUserChannels(token: String): Observable<String> =
        toUserId(token).flatMapObservable {
            db.child(USERS).child(it).child(CHANNELS).getObservableValues()
        }

    override fun getChannelInfo(channelId: String): Single<ChannelInfoEntity> =
        db.child(CHANNELS).child(channelId).getSingleValue()

    override fun addChannel(info: ChannelInfoEntity): Single<String> =
        Single.create {
            val push = db.child(CHANNELS).push()
            val channelId = push.key!!
            info.id = channelId
            push.setValue(info)
            db.child(USERS).child(info.u1).child(CHANNELS).push().setValue(channelId)
            db.child(USERS).child(info.u2).child(CHANNELS).push().setValue(channelId)
            it.onSuccess(channelId)
        }

    override fun getMessages(channelId: String): Observable<MessageEntity> =
        db.child(CHANNELS).child(channelId).child(MESSAGES).getObservableValues()

    override fun observeChannels(token: String): Observable<String> =
        toUserId(token).flatMapObservable {
            db.child(USERS).child(it).child(CHANNELS).observe()
        }
}