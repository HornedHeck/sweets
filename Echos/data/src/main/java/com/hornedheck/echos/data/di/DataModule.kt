package com.hornedheck.echos.data.di

import com.hornedheck.echos.data.api.MessagesApi
import com.hornedheck.echos.data.api.TempMessagesApi
import com.hornedheck.echos.data.repo.MessagesRepo
import com.hornedheck.echos.data.repo.MessagesRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesMessagesApi(): MessagesApi = TempMessagesApi()

    @Provides
    @Singleton
    fun providesMessagesRepository(api: MessagesApi): MessagesRepo = MessagesRepoImpl(api)

}