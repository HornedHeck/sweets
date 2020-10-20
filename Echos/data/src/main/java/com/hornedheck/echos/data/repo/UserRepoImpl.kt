package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.api.models.toEntity
import com.hornedheck.echos.data.api.models.toUser
import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.core.Maybe

internal class UserRepoImpl(
    private val api: UserApi
) : UserRepo {

    override fun login(user: User) = api.loginUser(user.toEntity())

    override fun checkName(name: String) = api.checkName(name)

    override fun isRegistered(email: String): Maybe<User> =
        api.isRegistered(email).map { it.toUser() }
}