package com.scoto.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.RecipeDb
import com.scoto.data.local.local_dto.UserDb
import java.lang.reflect.Type

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.02.2022 at 14:24
 */
@ProvidedTypeConverter
class JsonConverter {

    private fun <T> type(): Type = object : TypeToken<T>() {}.type

    private fun <T> toJson(src: Any): String {
        return Gson().toJson(src, type<T>())
    }

    private fun <T> fromJson(src: String): T {
        return Gson().fromJson(src, type<T>())
    }

    @TypeConverter
    fun recipeListToJson(recipes: List<RecipeDb>): String {
        return toJson<List<RecipeDb>>(recipes)
    }

    @TypeConverter
    fun jsonToRecipeList(recipes: String): List<RecipeDb> {
        return fromJson<List<RecipeDb>>(recipes)
    }

    @TypeConverter
    fun userToJson(user: UserDb): String {
        return toJson<UserDb>(user)
    }

    @TypeConverter
    fun jsonToUser(user: String): UserDb {
        return fromJson<UserDb>(user)
    }

    @TypeConverter
    fun categoryToJson(category: CategoryDb): String {
        return toJson<CategoryDb>(category)
    }

    @TypeConverter
    fun jsonToCategory(category: String): CategoryDb {
        return fromJson<CategoryDb>(category)
    }
}
