package com.scoto.fodamy.di.modules

import com.scoto.data.network.repositories.DefaultAuthRepository
import com.scoto.data.network.repositories.DefaultRecipeRepository
import com.scoto.data.network.repositories.DefaultUserRepository
import com.scoto.data.network.services.AuthService
import com.scoto.data.network.services.RecipeService
import com.scoto.data.network.services.UserService
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.repositories.UserRepository
import com.scoto.domain.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 10:18
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        dataStoreManager: DataStoreManager
    ): AuthRepository {
        return DefaultAuthRepository(authService, dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository {
        return DefaultUserRepository(userService)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeService: RecipeService): RecipeRepository {
        return DefaultRecipeRepository(recipeService)
    }
}