package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.api.models.toEntity
import com.hornedheck.echos.data.api.models.toUser
import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

internal class UserRepoImpl(
    private val api: UserApi
) : UserRepo {

    override fun login(user: User): Completable =
        api.loginUser(user.toEntity())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun checkName(name: String): Single<Boolean> =
        api.checkName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun isRegistered(email: String): Maybe<User> =
        api.isRegistered(email).map { it.toUser() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}