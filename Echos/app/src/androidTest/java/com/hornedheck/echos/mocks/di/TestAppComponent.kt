package com.hornedheck.echos.mocks.di

import com.hornedheck.echos.di.AppComponent
import com.hornedheck.echos.di.NavigationModule
import com.hornedheck.echos.domain.di.DomainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestDataModule::class, DomainModule::class, NavigationModule::class])
interface TestAppComponent : AppComponent {}