package com.scoto.fodamy.di.modules

import com.scoto.fodamy.network.api.AuthService
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.AuthRepositoryImpl
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.network.repositories.RecipeRepositoryImpl
import com.scoto.fodamy.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    //Services
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }


    //Repositories
    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }


    @Provides
    @Singleton
    fun provideRecipeRepository(recipeService: RecipeService): RecipeRepository {
        return RecipeRepositoryImpl(recipeService)
    }

}