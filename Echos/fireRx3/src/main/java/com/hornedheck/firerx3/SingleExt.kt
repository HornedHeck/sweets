package com.hornedheck.firerx3

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.reactivex.rxjava3.core.Single

inline fun <reified T : Any> DatabaseReference.getSingleValue(): Single<T> = Single.create {
    this.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) =
            snapshot.getValue<T>()?.let(it::onSuccess) ?: run { it.onError(NullPointerException()) }

        override fun onCancelled(error: DatabaseError) {
            it.onError(error.toException())
        }
    })
}