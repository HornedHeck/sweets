package com.hornedheck.echos.data.api

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.ReplaySubject
import io.reactivex.rxjava3.subjects.Subject
import kotlin.reflect.KClass

class FirebaseMessagesApi : MessagesApi {

    companion object {
        private const val MESSAGES = "messages"
        private const val USERS = "users"
        private const val CONTACTS = "contacts"
    }

    private val db = Firebase.database.reference

    //  TODO replace with Firebase Auth
    private val id = "ID"

    private val contactsObservable = ReplaySubject.create<User>()


    init {
        db.child(USERS).child(id).child(CONTACTS).addChildEventListener(
            ObservableChildListener(contactsObservable, User::class)
        )
    }

    override fun observeContracts(): Observable<User> {
        return contactsObservable
    }

    override fun addContact(link: String): Boolean {
        val items = db.child(USERS).values<User>().blockingGet()
        val user = items.firstOrNull { it.link == link }
        user?.let {
            db.child(USERS).child(id).child(CONTACTS).push().setValue(it.id)
        }
        return user != null
    }


    private inline fun <reified T : Any> DatabaseReference.values(): Single<List<T>> {
        return Single.create {
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    it.onSuccess(
                        snapshot.children.mapNotNull { item -> item.getValue<T>() }.toList()
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    it.tryOnError(error.toException())
                }
            })
        }
    }
}


class ObservableChildListener<T : Any>(
    private val subject: Subject<T>,
    private val cls: KClass<T>
) : ChildEventListener {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        subject.onNext(snapshot.getValue(cls.java))
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

    override fun onChildRemoved(snapshot: DataSnapshot) {}

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

    override fun onCancelled(error: DatabaseError) {
        subject.onError(error.toException())
    }
}
