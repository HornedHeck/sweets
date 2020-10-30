package com.hornedheck.echos.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FlowScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope