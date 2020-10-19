package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MessagesApi {

    fun observeContracts(): Observable<ChannelInfoEntity>

    fun getContact(link: String): Maybe<UserEntity>

    fun addContact(info: ChannelInfoEntity): Completable

    fun createChannel(user: UserEntity): Single<ChannelInfoEntity>

    fun getChannels(): Observable<ChannelInfoEntity>

}