package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.toEntity
import com.hornedheck.echos.data.api.models.toInfo
import com.hornedheck.echos.data.api.models.toUser
import com.hornedheck.echos.data.models.ChannelInfo
import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

internal class MessagesRepoImpl(
    private val api: MessagesApi
) : MessagesRepo {

    private lateinit var token: String

    override fun login(user: User): Completable {
        token = user.token
        return api.loginUser(user.toEntity())
    }

    override fun observeContracts(): Observable<ChannelInfo> =
        api.observeChannels(token)
            .flatMapSingle(api::getChannelInfo)
            .flatMapSingle { channel ->
                if (channel.u1 == token) {
                    api.getUser(channel.u2)
                } else {
                    api.getUser(channel.u1)
                }.map { channel.toInfo(it.toUser()) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun findUserWithTimeout(
        link: String,
        timeout: Long = 5L,
        units: TimeUnit = TimeUnit.SECONDS
    ) = api.findUser(link).timeout(timeout, units)

    override fun addContact(link: String): Completable =
        findUserWithTimeout(link)
            .flatMapCompletable { user ->
                user?.let {
                    api.getUserChannels(it)
                        .flatMapSingle { ch -> api.getChannelInfo(ch) }
                        .any { ch -> ch.u1 == it || ch.u2 == it }
                        .flatMap { exists ->
                            if (exists) {
                                Single.error(Exception("duplicate"))
                            } else {
                                val info = ChannelInfoEntity(u1 = token, u2 = user)
                                api.addChannel(info)
                            }
                        }.ignoreElement()
                } ?: Completable.error(NullPointerException())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}