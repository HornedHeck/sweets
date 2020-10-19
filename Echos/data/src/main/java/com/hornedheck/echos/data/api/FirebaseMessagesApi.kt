package com.hornedheck.echos.data.api

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.ReplaySubject
import io.reactivex.rxjava3.subjects.Subject

class FirebaseMessagesApi : MessagesApi {

    companion object {
        private const val MESSAGES = "messages"
        private const val CHANNELS = "channels"
        private const val USERS = "users"
    }

    private val db = Firebase.database.reference

    //  TODO replace with Firebase Auth
    private val id = "ID"

    override fun getContact(link: String): Maybe<UserEntity> =
        db.child(USERS).values { it.getValue<UserEntity>()!! }
            .filter { it.link == link }
            .firstElement()

    override fun addContact(info: ChannelInfoEntity): Completable {
        return Completable.create {
            db.child(USERS).child(id).child(CHANNELS).push().setValue(info)
        }
    }

    override fun createChannel(user: UserEntity): Single<ChannelInfoEntity> = Single.create {
        val push = db.child(CHANNELS).push()
        val info = ChannelInfoEntity(push.key!!, user.name, user.link)
        it.onSuccess(info)
    }

    override fun getChannels(): Observable<ChannelInfoEntity> =
        db.child(USERS).child(id).child(CHANNELS)
            .values { it.getValue<ChannelInfoEntity>()!! }

    override fun observeContracts(): Observable<ChannelInfoEntity> {
        val subject = ReplaySubject.create<ChannelInfoEntity>()
        val listener = ObservableChildListener(subject) { it.getValue<ChannelInfoEntity>()!! }
        db.child(USERS).child(id).child(CHANNELS).addChildEventListener(listener)
        subject.doOnDispose {
            db.child(USERS).child(id).child(CHANNELS).removeEventListener(listener)
        }
        return subject
    }
}


class ObservableChildListener<T : Any>(
    private val subject: Subject<T>,
    private val converter: (DataSnapshot) -> T
) : ChildEventListener {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        subject.onNext(converter(snapshot))
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

    override fun onChildRemoved(snapshot: DataSnapshot) {}

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

    override fun onCancelled(error: DatabaseError) {
        subject.onError(error.toException())
    }
}

private inline fun <reified T : Any?> DatabaseReference.values(crossinline converter: (DataSnapshot) -> T): Observable<T> {
    return Observable.create { emitter ->
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.map { converter(it) }.forEach(emitter::onNext)
            }

            override fun onCancelled(error: DatabaseError) {
                emitter.tryOnError(error.toException())
            }
        })
    }
}