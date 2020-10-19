package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.toInfo
import com.hornedheck.echos.data.models.ChannelInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

internal class MessagesRepoImpl(
    private val api: MessagesApi
) : MessagesRepo {

    override fun observeContracts(): Observable<ChannelInfo> =
        api.observeContracts().map(ChannelInfoEntity::toInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun addContact(link: String, timeout: Long): Completable =
        api.getChannels()
            .all { it.link != link }
            .timeout(timeout, TimeUnit.SECONDS)
            .onErrorResumeWith(Single.just(true))
            .flatMapCompletable { isNew ->
                return@flatMapCompletable if (isNew) {
                    api.getContact(link)
                        .flatMapSingle(api::createChannel)
                        .flatMapCompletable(api::addContact)
                } else {
                    Completable.error(Exception())
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}