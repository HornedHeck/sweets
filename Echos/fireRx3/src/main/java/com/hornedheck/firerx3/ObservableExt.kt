package com.hornedheck.firerx3

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

inline fun <reified T : Any> DatabaseReference.getObservableValues(): Observable<T> =
    Observable.create {
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .mapNotNull { child -> child.getValue<T>() }
                    .forEach(it::onNext)
                it.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                it.onError(error.toException())
            }
        })
    }

inline fun <reified T : Any> DatabaseReference.observe(): Observable<T> {
    val subject = PublishSubject.create<T>()
    val listener = object : ChildEventListener {

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) =
            snapshot.getValue<T>().let(subject::onNext)

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) = subject.onError(error.toException())
    }
    subject.doOnDispose { removeEventListener(listener) }
    return subject
}