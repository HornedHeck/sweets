package com.hornedheck.echos.mocks

import com.hornedheck.echos.domain.models.User
import com.hornedheck.echos.domain.repo.UserRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import java.util.*

class MockUserRepo : UserRepo {

    override var id: String = "718927498"

    override fun login(user: User, new: Boolean): Completable {
        id = UUID.randomUUID().toString()
        return Completable.complete()
    }

    override fun checkName(name: String): Single<Boolean> {
        return Single.just(name.length < 10)
    }

    override fun isRegistered(email: String): Maybe<User> {
        return Maybe.empty()
    }

    override fun getUser(id: String): Single<User> {
        return Single.just(User(
            id,
            "Name of $id",
            "email_of_$id@gmail.com",
            "@link_of_$id"
        ))
    }

    override fun getUserByLink(link: String): Single<User> {
        return Single.just(User(
            id,
            "Name of $id",
            "email_of_$id@gmail.com",
            "@link_of_$id"
        ))
    }
}