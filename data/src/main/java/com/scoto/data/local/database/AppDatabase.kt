package com.scoto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scoto.data.local.converters.CategoryConverter
import com.scoto.data.local.converters.ImageConverter
import com.scoto.data.local.converters.JsonConverter
import com.scoto.data.local.converters.UserConverter
import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.UserDao
import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.CommentDb
import com.scoto.data.local.local_dto.ImageDb
import com.scoto.data.local.local_dto.RecipeDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:48
 */
@Database(
    entities = [
        RecipeDb::class,
        CategoryDb::class,
        ImageDb::class,
        CommentDb::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(
    UserConverter::class,
    CategoryConverter::class,
    ImageConverter::class,
    JsonConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao
}
