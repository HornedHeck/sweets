package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.api.models.ChannelInfoEntity
import com.hornedheck.echos.data.api.models.MessageEntity
import com.hornedheck.echos.data.api.models.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MessagesApi {

    /** Returns full user info
     *  @param token User id
     *  @return info about user (name, link, etc)
     *  @see UserEntity
     */
    fun getUser(token: String): Single<UserEntity>

    /** Updates token for specified user
     *  @param user user info with new token
     *  @see UserEntity
     */
    fun loginUser(user: UserEntity): Completable

    /** Tries to find user by link
     *  @param link link to user (@...)
     *  @return user if it exists
     *  @see UserEntity
     */
    fun findUser(link: String): Single<String>

    /** Returns all channels for specified user
     *  @param token User id
     *  @return channel Ids for user
     */
    fun getUserChannels(token: String): Observable<String>

    /** Return full channel info for specified channel
     *  @param channelId Channel Id
     *  @return full channel info
     *  @see ChannelInfoEntity
     */
    fun getChannelInfo(channelId: String): Single<ChannelInfoEntity>

    /** Add channel to channels list, user1 channels and user2 channels
     *  @param info info about chanel to insert
     *  @return id of created channel
     */
    fun addChannel(info: ChannelInfoEntity): Single<String>

    /** Get messages from specified channel
     *  @param channelId Channel Id
     *  @return messages in channel
     *  @see MessageEntity
     */
    fun getMessages(channelId: String): Observable<MessageEntity>

    /** Observe add event on user channels and returns observable to it.
     *  @param token User id
     *  @return channel ids
     */
    fun observeChannels(token: String): Observable<String>

}