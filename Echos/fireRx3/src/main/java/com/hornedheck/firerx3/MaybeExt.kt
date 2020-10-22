package com.hornedheck.firerx3

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.rxjava3.core.Maybe
import kotlin.reflect.KClass

fun <T : Any> DatabaseReference.getMaybeValue(cls: KClass<T>): Maybe<T> =
    Maybe.create {
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) =
                snapshot.getValue(cls.java)?.let(it::onSuccess)
                    ?: run { it.onComplete() }

            override fun onCancelled(error: DatabaseError) {
                it.onError(error.toException())
            }
        })
    }