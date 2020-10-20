package com.hornedheck.echos.data.api

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.api.models.UserEntity
import com.hornedheck.firerx3.getObservableValues
import com.hornedheck.firerx3.getObservableValuesWithKey
import com.hornedheck.firerx3.getSingleValue
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

internal class UserApiImpl : UserApi {

    private val db = Firebase.database.reference

    private fun toUserId(token: String): Single<String> =
        db.child(USERS).getObservableValuesWithKey<UserEntity>()
            .filter { (_, user) -> user.token == token }
            .firstOrError()
            .map { (key, _) -> key }

    override fun getUser(token: String): Single<UserEntity> =
        toUserId(token).flatMap {
            db.child(USERS).child(it).getSingleValue()
        }

    override fun loginUser(user: UserEntity): Completable =
        db.child(USERS).getObservableValuesWithKey<UserEntity>()
            .filter { (_, entity) -> entity.email == user.email }
            .firstElement()
            .doOnSuccess { (key, user) ->
                db.child(USERS).child(key).child(TOKEN).setValue(user.token)
            }
            .doOnComplete {
                db.child(USERS).push().setValue(user)
            }
            .ignoreElement()


    override fun findUser(link: String): Single<UserEntity> =
        db.child(USERS).getObservableValues<UserEntity>()
            .filter { it.link == link }
            .firstOrError()

    override fun checkName(name: String): Single<Boolean> =
        db.child(USERS)
            .getObservableValues<UserEntity>()
            .all { it.name != name }

    override fun isRegistered(email: String): Maybe<UserEntity> =
        db.child(USERS)
            .getObservableValues<UserEntity>()
            .filter { it.email == email }
            .firstElement()

}