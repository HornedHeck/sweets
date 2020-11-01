package com.hornedheck.firerx3

import com.google.firebase.database.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import kotlin.reflect.KClass

fun <T : Any> DatabaseReference.getObservableValues(cls: KClass<T>): Observable<T> =
    Observable.create {
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .mapNotNull { child -> child.getValue(cls.java) }
                    .forEach(it::onNext)
                it.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                it.onError(error.toException())
            }
        })
    }

fun <T : Any> DatabaseReference.getObservableValuesWithKey(cls: KClass<T>): Observable<Pair<String, T>> =
    Observable.create { emitter ->
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .mapNotNull { child ->
                        child.key ?: return@mapNotNull null
                        child.getValue(cls.java)?.let { child.key!! to it }
                    }
                    .forEach(emitter::onNext)
                emitter.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                emitter.onError(error.toException())
            }
        })
    }

fun <T : Any> DatabaseReference.observe(cls: KClass<T>): Observable<ChildAction<T>> {
    val subject = PublishSubject.create<ChildAction<T>>()
    val listener = object : ChildEventListener {

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            Timber.d("Data added: $snapshot")
            snapshot.getValue(cls.java)?.let {
                subject.onNext(ChildAdd(it))
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            Timber.d("Data changed: $snapshot")
            snapshot.getValue(cls.java)?.let {
                subject.onNext(ChildUpdate(it))
            }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            Timber.d("Data removed: $snapshot")
            snapshot.getValue(cls.java)?.let {
                subject.onNext(ChildDelete(it))
            }
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) = subject.onError(error.toException())
    }
    addChildEventListener(listener)
    subject.doOnDispose { removeEventListener(listener) }
    return subject
}