package com.scoto.fodamy.di.modules

import com.scoto.data.utils.DataStoreManagerImpl
import com.scoto.domain.utils.DataStoreManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    abstract fun provideDataStoreManager(
        dataStoreManagerImpl: DataStoreManagerImpl
    ): DataStoreManager
}