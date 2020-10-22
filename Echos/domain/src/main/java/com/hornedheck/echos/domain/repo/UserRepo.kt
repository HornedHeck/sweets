package com.hornedheck.echos.domain.repo

import com.hornedheck.echos.domain.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface UserRepo {

    var token: String

    fun login(user: User): Completable

    fun checkName(name: String): Single<Boolean>

    fun isRegistered(email: String): Maybe<User>

    fun getUser(): Single<User>

    fun getUserById(id: String): Single<User>

    fun getUserByLink(link: String): Single<User>

}