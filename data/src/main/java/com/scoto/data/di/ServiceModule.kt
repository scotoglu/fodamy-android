package com.scoto.data.di

import android.content.Context
import com.scoto.data.BuildConfig
import com.scoto.data.remote.services.AuthService
import com.scoto.data.remote.services.RecipeService
import com.scoto.data.remote.services.UserService
import com.scoto.data.utils.AuthInterceptor
import com.scoto.data.utils.JsonReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:44
 */

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideInterceptor(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Services
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

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideJsonReader(
        @ApplicationContext context: Context
    ): JsonReader {
        return JsonReader(context)
    }
}
