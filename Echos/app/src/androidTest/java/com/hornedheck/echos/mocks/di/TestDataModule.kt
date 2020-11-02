package com.hornedheck.echos.mocks.di

import com.hornedheck.echos.data.api.ChannelsApi
import com.hornedheck.echos.data.api.MessageApi
import com.hornedheck.echos.data.api.UserApi
import com.hornedheck.echos.data.di.DataModule
import com.hornedheck.echos.domain.repo.ChannelsRepo
import com.hornedheck.echos.domain.repo.MessageRepo
import com.hornedheck.echos.domain.repo.UserRepo
import com.hornedheck.echos.mocks.MockChannelsRepo
import com.hornedheck.echos.mocks.MockMessagesRepo
import com.hornedheck.echos.mocks.MockUserRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDataModule : DataModule() {

    @Singleton
    @Provides
    override fun providesChannelsRepo(api: ChannelsApi): ChannelsRepo {
        return MockChannelsRepo()
    }

    @Singleton
    @Provides
    override fun providesUserRepo(api: UserApi): UserRepo {
        return MockUserRepo()
    }

    @Singleton
    @Provides
    override fun providesMessageRepo(api: MessageApi): MessageRepo {
        return MockMessagesRepo()
    }

}