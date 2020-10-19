package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.core.Observable

interface MessagesApi {

    fun observeContracts(): Observable<User>

    fun addContact(link: String): Boolean

}