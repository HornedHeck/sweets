package com.hornedheck.echos.data.api

import com.hornedheck.echos.data.models.UserEntity
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface UserApi {

    /** Checks if user is registered
     *  @param email username to check
     *  @return User if registered else completes
     */
    fun isRegistered(email: String): Maybe<UserEntity>

    /** Completes successfully if username is free and fall with error if occupied
     *  @param name username to check
     */
    fun checkName(name: String): Single<Boolean>

    /** Returns full user info
     *  @param id User id
     *  @return info about user (name, link, etc)
     *  @see UserEntity
     */
    fun getUserById(id: String): Single<UserEntity>

    /** Login specified user
     *  @param user user info with new token
     *  @see UserEntity
     */
    fun loginUser(user: UserEntity): Single<UserEntity>

    /** Register specified user
     *  @param user user info with new token
     *  @see UserEntity
     */
    fun registerUser(user : UserEntity) : Single<UserEntity>

    /** Tries to find user by link
     *  @param link link to user (@...)
     *  @return user if it exists
     *  @see UserEntity
     */
    fun findUser(link: String): Single<UserEntity>

}