package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.toInfo
import com.hornedheck.echos.data.api.models.toUser
import com.hornedheck.echos.data.models.ChannelInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

internal class MessagesRepoImpl(
    private val api: MessagesApi
) : MessagesRepo {

    //  TODO change with FirebaseAuth with Data Storage || Preferences
    private fun getToken() = "1"

    override fun observeContracts(): Observable<ChannelInfo> =
        api.observeChannels(getToken())
            .flatMapSingle(api::getChannelInfo)
            .flatMapSingle { channel ->
                if (channel.u1 == getToken()) {
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
                                val info = ChannelInfoEntity(u1 = getToken(), u2 = user)
                                api.addChannel(info)
                            }
                        }.ignoreElement()
                } ?: Completable.error(NullPointerException())
            }
            .timeout(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}