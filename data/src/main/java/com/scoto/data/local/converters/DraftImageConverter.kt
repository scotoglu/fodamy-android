package com.scoto.data.local.converters

import androidx.room.TypeConverter

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
class DraftImageConverter {

    @TypeConverter
    fun imagesToJson(images: List<String>): String {
        return toJson<List<String>>(images)
    }

    @TypeConverter
    fun jsonToImages(src: String): List<String> {
        return fromJson(src)
    }
}
