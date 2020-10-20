package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface UserRepo {


    fun login(user: User): Completable

    fun checkName(name: String): Single<Boolean>

    fun isRegistered(email: String): Maybe<User>

}