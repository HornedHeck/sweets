package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.models.ChannelInfo
import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MessagesRepo {

    fun observeContracts(): Observable<ChannelInfo>

    fun addContact(link: String, timeout: Long): Completable
}