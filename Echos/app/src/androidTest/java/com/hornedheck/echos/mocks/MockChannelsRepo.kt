package com.hornedheck.echos.mocks

import com.hornedheck.echos.domain.models.ChannelInfoPartial
import com.hornedheck.echos.domain.repo.ChannelsRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class MockChannelsRepo : ChannelsRepo {

    private var contacts = PublishSubject.create<ChannelInfoPartial>()

    override fun observeContracts(id: String): Observable<ChannelInfoPartial> {
        return contacts
    }

    override fun getContacts(id: String): Observable<ChannelInfoPartial> {
        return Observable.just(
            ChannelInfoPartial("1", "1", "2"),
            ChannelInfoPartial("2", "3", "4"),
        )
    }

    override fun addContact(id1: String, id2: String): Completable {
        contacts.onNext(ChannelInfoPartial(UUID.randomUUID().toString(), id1, id2))
        return Completable.complete()
    }

    override fun getUnread(channel: String, to: String): Single<Int> {
        return Single.just(12)
    }
}