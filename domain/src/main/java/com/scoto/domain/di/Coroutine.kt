package com.scoto.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

/**
 * @author Sefa ÇOTOĞLU
 * Created 31.01.2022 at 11:49
 */
@Module
@InstallIn(SingletonComponent::class)
object Coroutine {

    @Provides
    @UseCaseDispatcher
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @UseCaseScope
    fun provideScope(): CoroutineScope = CoroutineScope(SupervisorJob() + provideDispatcher())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UseCaseDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UseCaseScope
