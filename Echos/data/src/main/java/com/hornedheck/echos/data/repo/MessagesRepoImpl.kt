package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.models.ChannelInfoEntity
import com.hornedheck.echos.data.models.toInfo
import com.hornedheck.echos.domain.models.ChannelInfoPartial
import com.hornedheck.echos.domain.repo.ChannelsRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

internal class MessagesRepoImpl(
    private val api: MessagesApi,
) : ChannelsRepo {

    override fun observeContracts(token: String): Observable<ChannelInfoPartial> =
        api.observeChannels(token)
            .flatMapSingle(api::getChannelInfo)
            .map(ChannelInfoEntity::toInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addContact(token1: String, token2: String): Completable =
        api.addChannel(token1, token2)
            .ignoreElement()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getContacts(token: String): Observable<ChannelInfoPartial> =
        api.getUserChannels(token)
            .flatMapSingle(api::getChannelInfo)
            .map(ChannelInfoEntity::toInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}