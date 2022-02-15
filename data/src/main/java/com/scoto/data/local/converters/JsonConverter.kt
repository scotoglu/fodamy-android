package com.scoto.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.scoto.data.local.local_dto.RecipeDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.02.2022 at 14:24
 */
@ProvidedTypeConverter
class JsonConverter {

    @TypeConverter
    fun recipeListToJson(recipes: List<RecipeDb>): String {
        return toJson<List<RecipeDb>>(recipes)
    }

    @TypeConverter
    fun jsonToRecipeList(recipes: String): List<RecipeDb> {
        return fromJson<List<RecipeDb>>(recipes)
    }
}
