package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.models.ChannelInfoEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MessagesApi {

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
     *  @param u1 first user token
     *  @param u2 second user token
     *  @return id of created channel
     */
    fun addChannel(u1 : String , u2 : String): Single<String>

//    /** Get messages from specified channel
//     *  @param channelId Channel Id
//     *  @return messages in channel
//     *  @see MessageEntity
//     */
//    fun getMessages(channelId: String): Observable<MessageEntity>

    /** Observe add event on user channels and returns observable to it.
     *  @param token User id
     *  @return channel ids
     */
    fun observeChannels(token: String): Observable<String>

//    /** Checks fox existing channel between 2 users
//     *  @param user1 token of first user
//     *  @param user2 token of second user
//     *  @return true - everything is ok, false - channel already exists
//     */
//    fun checkChannel(user1: String, user2: String): Single<Boolean>

}