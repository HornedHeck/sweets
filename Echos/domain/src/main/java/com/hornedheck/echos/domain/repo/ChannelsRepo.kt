package com.hornedheck.echos.domain.repo

import com.hornedheck.echos.domain.models.ChannelInfoPartial
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ChannelsRepo {

    fun observeContracts(id: String): Observable<ChannelInfoPartial>

    fun getContacts(id: String): Observable<ChannelInfoPartial>

    fun addContact(id1: String, id2: String): Completable

    fun getUnread(channel: String, to: String): Single<Int>

}