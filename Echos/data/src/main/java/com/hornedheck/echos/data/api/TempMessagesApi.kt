package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.models.User
import io.reactivex.rxjava3.subjects.ReplaySubject

class TempMessagesApi : MessagesApi {

    private val users = arrayOf(
        User(
            "1",
            "hornedheck",
            "@hornedheck"
        ),
        User(
            "2",
            "vitaly",
            "@vit"
        )
    )

    private val contactsObservable = ReplaySubject.create<User>()

    override fun addContact(link: String): Boolean {
        val user = users.firstOrNull { it.link == link }
        user?.let(contactsObservable::onNext)
        return user != null
    }

    override fun observeContracts(): ReplaySubject<User> = contactsObservable
}