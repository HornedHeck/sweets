package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.api.models.toInfo
import com.hornedheck.echos.data.api.models.toUser
import com.hornedheck.echos.data.models.ChannelInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

internal class MessagesRepoImpl(
    private val api: MessagesApi,
    private val userApi: UserApi,
) : MessagesRepo {

    private lateinit var token: String

    override fun setToken(token: String) {
        this.token = token
    }

    override fun observeContracts(): Observable<ChannelInfo> =
        api.observeChannels(token)
            .flatMapSingle(api::getChannelInfo)
            .flatMapSingle { channel ->
                if (channel.u1 == token) {
                    userApi.getUser(channel.u2)
                } else {
                    userApi.getUser(channel.u1)
                }.map { channel.toInfo(it.toUser()) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addContact(link: String): Completable =
        userApi.findUser(link)
            .flatMapCompletable { u2 ->
                api.checkChannel(token, u2.token).flatMapCompletable {
                    if (it) {
                        api.addChannel(token, u2.token).ignoreElement()
                    } else {
                        Completable.error(Exception())
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}