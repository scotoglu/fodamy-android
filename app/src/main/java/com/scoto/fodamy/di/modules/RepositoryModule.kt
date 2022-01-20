package com.scoto.fodamy.di.modules

import com.scoto.data.network.repositories.DefaultAuthRepository
import com.scoto.data.network.repositories.DefaultRecipeRepository
import com.scoto.data.network.repositories.DefaultUserRepository
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 10:18
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(
        defaultAuthRepository: DefaultAuthRepository
    ): AuthRepository

    @Binds
    abstract fun provideUserRepository(defaultUserRepository: DefaultUserRepository): UserRepository

    @Binds
    abstract fun provideRecipeRepository(defaultRecipeRepository: DefaultRecipeRepository): RecipeRepository
}