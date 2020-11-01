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

    private val id
        get() = userRepo.id

    private fun ChannelInfoPartial.toFullInfo(): Single<ChannelInfo> =
        if (userId1 == id) {
            userRepo.getUser(userId2).map { ChannelInfo(channelId, it) }
        } else {
            userRepo.getUser(userId1).map { ChannelInfo(channelId, it) }
        }

    fun getChannels(): Observable<ChannelInfo> =
        channelsRepo.getContacts(id)
            .flatMapSingle { it.toFullInfo() }

    fun observeChannels(): Observable<ChannelInfo> =
        channelsRepo.observeContracts(id)
            .flatMapSingle { it.toFullInfo() }

    fun addContact(link: String): Completable =
        userRepo.getUserByLink(link)
            .flatMapCompletable {
                if (it.id == id) {
                    Completable.error(Exception())
                } else {
                    channelsRepo.addContact(id, it.id)
                }
            }

    /** First - unread, second - channels */
    fun getUnreadCount(): Single<Pair<Int, Int>> =
        channelsRepo.getContacts(id)
            .flatMapSingle { channelsRepo.getUnread(it.channelId, id) }
            .toList()
            .map { it.sum() to it.size }
}