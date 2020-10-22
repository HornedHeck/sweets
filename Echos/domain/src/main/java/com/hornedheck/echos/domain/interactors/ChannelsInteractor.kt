package com.hornedheck.echos.domain.interactors

import com.hornedheck.echos.domain.models.ChannelInfo
import com.hornedheck.echos.domain.models.ChannelInfoPartial
import com.hornedheck.echos.domain.repo.ChannelsRepo
import com.hornedheck.echos.domain.repo.UserRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class ChannelsInteractor(
    private val userRepo: UserRepo,
    private val channelsRepo: ChannelsRepo
) {

    private val token
        get() = userRepo.token

    private fun ChannelInfoPartial.toFullInfo(): Single<ChannelInfo> =
        userRepo.getUserById(userId1).flatMap { u1 ->
            userRepo.getUserById(userId2).map { u2 ->
                if (u1.token == token) {
                    ChannelInfo(channelId, u2)
                } else {
                    ChannelInfo(channelId, u1)
                }
            }
        }

    fun getChannels(): Observable<ChannelInfo> =
        channelsRepo.getContacts(token)
            .flatMapSingle { it.toFullInfo() }

    fun observeChannels(): Observable<ChannelInfo> =
        channelsRepo.observeContracts(token)
            .flatMapSingle { it.toFullInfo() }

    fun addContact(link: String): Completable =
        userRepo.getUserByLink(link)
            .flatMapCompletable {
                if (it.token == token) {
                    Completable.error(Exception())
                } else {
                    channelsRepo.addContact(token, it.token)
                }
            }

}