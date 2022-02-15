package com.scoto.data.local.converters

import androidx.room.TypeConverter
import com.scoto.data.local.local_dto.CategoryDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.02.2022 at 13:58
 */
class CategoryConverter {

    @TypeConverter
    fun categoryToJson(category: CategoryDb): String {
        return toJson<CategoryDb>(category)
    }

    @TypeConverter
    fun jsonToCategory(category: String): CategoryDb {
        return fromJson<CategoryDb>(category)
    }
}