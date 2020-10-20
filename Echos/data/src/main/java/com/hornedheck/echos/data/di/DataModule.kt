package com.hornedheck.echos.data.di

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.MessagesApiImpl
import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.api.UserApiImpl
import com.hornedheck.echos.data.repo.MessagesRepo
import com.hornedheck.echos.data.repo.MessagesRepoImpl
import com.hornedheck.echos.data.repo.UserRepo
import com.hornedheck.echos.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesMessagesApi(): MessagesApi = MessagesApiImpl()

    @Provides
    @Singleton
    fun providesMessagesRepository(api: MessagesApi, userApi: UserApi): MessagesRepo =
        MessagesRepoImpl(api, userApi)

    @Provides
    @Singleton
    fun provideUserApi(): UserApi = UserApiImpl()

    @Provides
    @Singleton
    fun providesUserRepo(api: UserApi): UserRepo = UserRepoImpl(api)
}