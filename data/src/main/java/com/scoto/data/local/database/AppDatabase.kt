package com.scoto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.UserDao
import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.ImageDb
import com.scoto.data.local.local_dto.RecipeDb
import com.scoto.data.local.local_dto.UserDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:48
 */
@Database(
    entities = [RecipeDb::class, CategoryDb::class, UserDb::class, ImageDb::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao
}
