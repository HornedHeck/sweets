package com.hornedheck.echos.data.di

import com.hornedheck.echos.data.api.*
import com.hornedheck.echos.data.repo.ChanelsRepoImpl
import com.hornedheck.echos.data.repo.MessageRepoImpl
import com.hornedheck.echos.data.repo.UserRepoImpl
import com.hornedheck.echos.domain.repo.ChannelsRepo
import com.hornedheck.echos.domain.repo.MessageRepo
import com.hornedheck.echos.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesMessagesApi(): ChannelsApi = ChannelsApiImpl()

    @Provides
    @Singleton
    fun providesMessagesRepository(api: ChannelsApi): ChannelsRepo = ChanelsRepoImpl(api)

    @Provides
    @Singleton
    fun provideUserApi(): UserApi = UserApiImpl()

    @Provides
    @Singleton
    fun providesUserRepo(api: UserApi): UserRepo = UserRepoImpl(api)

    @Provides
    @Singleton
    fun providesMessageApi(): MessageApi = MessageApiImpl()

    @Provides
    @Singleton
    fun providesMessageRepo(api: MessageApi): MessageRepo = MessageRepoImpl(api)

}