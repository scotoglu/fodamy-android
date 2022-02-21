package com.scoto.fodamy.di.modules

import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.UserDao
import com.scoto.data.local.database.AppDatabase
import com.scoto.data.remote.services.AuthService
import com.scoto.data.remote.services.RecipeService
import com.scoto.data.remote.services.UserService
import com.scoto.data.repositories.DefaultAuthRepository
import com.scoto.data.repositories.DefaultRecipeRepository
import com.scoto.data.repositories.DefaultUserRepository
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
    fun provideUserRepository(userService: UserService,userDao: UserDao): UserRepository {
        return DefaultUserRepository(userService,userDao)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDao
    ): RecipeRepository {
        return DefaultRecipeRepository(recipeService, recipeDao)
    }
}
