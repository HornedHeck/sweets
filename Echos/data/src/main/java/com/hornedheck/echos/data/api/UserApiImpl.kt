package com.hornedheck.echos.data.api

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hornedheck.echos.data.models.UserEntity
import com.hornedheck.firerx3.getObservableValues
import com.hornedheck.firerx3.getObservableValuesWithKey
import com.hornedheck.firerx3.getSingleValue
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

internal class UserApiImpl : UserApi {

    private val db = Firebase.database.reference

    override fun loginUser(user: UserEntity): Single<UserEntity> =
        db.child(USERS).getObservableValuesWithKey(UserEntity::class)
            .filter { (_, entity) -> entity.email == user.email }
            .firstOrError()
            .doOnSuccess { it.second.id = it.first }
            .map { it.second }

    override fun registerUser(user: UserEntity): Single<UserEntity> {
        val push = db.child(USERS).push()
        push.setValue(user)
        user.id = push.key!!
        return Single.just(user)
    }

    override fun findUser(link: String): Single<UserEntity> =
        db.child(USERS).getObservableValuesWithKey(UserEntity::class)
            .filter { it.second.link == link }
            .map {
                it.second.id = it.first
                it.second
            }
            .firstOrError()

    override fun checkName(name: String): Single<Boolean> =
        db.child(USERS)
            .getObservableValues(UserEntity::class)
            .all { it.name != name }

    override fun isRegistered(email: String): Maybe<UserEntity> =
        db.child(USERS)
            .getObservableValues(UserEntity::class)
            .filter { it.email == email }
            .firstElement()

    override fun getUserById(id: String): Single<UserEntity> =
        db.child(USERS).child(id).getSingleValue(UserEntity::class)
}