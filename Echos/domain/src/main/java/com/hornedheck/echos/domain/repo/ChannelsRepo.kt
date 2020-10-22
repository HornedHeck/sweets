package com.hornedheck.echos.domain.repo

import com.hornedheck.echos.domain.models.ChannelInfoPartial
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface ChannelsRepo {

    fun observeContracts(token: String): Observable<ChannelInfoPartial>

    fun getContacts(token: String): Observable<ChannelInfoPartial>

    fun addContact(token1: String, token2: String): Completable

}