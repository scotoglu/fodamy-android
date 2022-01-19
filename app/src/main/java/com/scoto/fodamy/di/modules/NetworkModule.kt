package com.scoto.fodamy.di.modules

import com.scoto.fodamy.network.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideInterceptor(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//    }


//    // Repositories
//    @Provides
//    @Singleton
//    fun provideAuthRepository(
//        authService: AuthService,
//        dataStoreManager: DataStoreManager
//    ): AuthRepository {
//        return AuthRepositoryImpl(authService, dataStoreManager)
//    }
//
//    @Provides
//    @Singleton
//    fun provideRecipeRepository(recipeService: RecipeService): RecipeRepository {
//        return RecipeRepositoryImpl(recipeService)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRepository(userService: UserService): UserRepository {
//        return UserRepositoryImpl(userService)
//    }
}
