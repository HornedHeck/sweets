package com.hornedheck.echos.domain.di

import com.hornedheck.echos.domain.interactors.ChannelsInteractor
import com.hornedheck.echos.domain.interactors.MessageInteractor
import com.hornedheck.echos.domain.repo.ChannelsRepo
import com.hornedheck.echos.domain.repo.MessageRepo
import com.hornedheck.echos.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesChannelsInteractor(
        userRepo: UserRepo,
        channelsRepo: ChannelsRepo,
    ) = ChannelsInteractor(
        userRepo,
        channelsRepo
    )

    @Provides
    @Singleton
    fun providesMessageInteractor(
        messageRepo: MessageRepo,
        userRepo: UserRepo,
    ) = MessageInteractor(
        messageRepo,
        userRepo
    )

}