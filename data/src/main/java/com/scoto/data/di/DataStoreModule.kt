package com.scoto.data.di

import com.scoto.data.utils.DataStoreManagerImpl
import com.scoto.domain.utils.DataStoreManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 09:49
 */
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class DataStoreModule {
//
//    @Binds
//    abstract fun provideDataStore(dataStoreManagerImpl: DataStoreManagerImpl):DataStoreManager
//
//}