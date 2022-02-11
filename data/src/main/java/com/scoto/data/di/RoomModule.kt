package com.scoto.data.di

import android.content.Context
import androidx.room.Room
import com.scoto.data.BuildConfig
import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.UserDao
import com.scoto.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:46
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.recipeDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.DBNAME
        ).build()
    }
}
