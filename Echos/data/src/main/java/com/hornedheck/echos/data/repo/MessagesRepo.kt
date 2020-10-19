package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.core.Observable

interface MessagesRepo {

    fun observeContracts(): Observable<User>

    fun addContact(link: String): Boolean

}