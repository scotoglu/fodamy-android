package com.scoto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scoto.data.local.converters.CategoryConverter
import com.scoto.data.local.converters.ImageListConverter
import com.scoto.data.local.converters.RecipeListConverter
import com.scoto.data.local.converters.UserConverter
import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.RemoteKeysDao
import com.scoto.data.local.dao.UserDao
import com.scoto.data.local.local_dto.*

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:48
 */
@Database(
    entities = [
        RecipeDb::class,
        CategoryDb::class,
        CommentDb::class,
        UserDb::class,
        RemoteKeysEditor::class,
        RemoteKeysLast::class,
        RemoteKeyRecipesByCategory::class
    ],
    version = 19,
    exportSchema = false
)
@TypeConverters(
    UserConverter::class,
    CategoryConverter::class,
    RecipeListConverter::class,
    ImageListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
