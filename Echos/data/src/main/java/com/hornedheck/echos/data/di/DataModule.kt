package com.hornedheck.echos.data.di

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.MessagesApiImpl
import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.api.UserApiImpl
import com.hornedheck.echos.data.repo.MessagesRepoImpl
import com.hornedheck.echos.data.repo.UserRepoImpl
import com.hornedheck.echos.domain.repo.ChannelsRepo
import com.hornedheck.echos.domain.repo.UserRepo
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
    fun providesMessagesRepository(api: MessagesApi): ChannelsRepo = MessagesRepoImpl(api)

    @Provides
    @Singleton
    fun provideUserApi(): UserApi = UserApiImpl()

    @Provides
    @Singleton
    fun providesUserRepo(api: UserApi): UserRepo = UserRepoImpl(api)
}