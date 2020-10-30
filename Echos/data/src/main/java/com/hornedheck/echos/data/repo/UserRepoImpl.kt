package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.models.UserEntity
import com.hornedheck.echos.data.models.toEntity
import com.hornedheck.echos.data.models.toUser
import com.hornedheck.echos.domain.models.User
import com.hornedheck.echos.domain.repo.UserRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

internal class UserRepoImpl(
    private val api: UserApi
) : UserRepo {

    override lateinit var id: String

    override fun login(user: User, new: Boolean): Completable =
        if (new) {
            api.registerUser(user.toEntity())
        } else {
            api.loginUser(user.toEntity())
        }
            .doOnSuccess { id = it.id }
            .ignoreElement()
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

    override fun getUser(id: String): Single<User> =
        api.getUserById(id)
            .map(UserEntity::toUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getUserByLink(link: String): Single<User> =
        api.findUser(link)
            .map(UserEntity::toUser)
            .timeout(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}