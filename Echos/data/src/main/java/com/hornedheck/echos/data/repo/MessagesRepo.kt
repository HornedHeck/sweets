package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.models.ChannelInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MessagesRepo {

    fun observeContracts(): Observable<ChannelInfo>

    fun addContact(link: String): Completable



}