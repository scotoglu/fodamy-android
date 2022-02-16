package com.scoto.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.scoto.data.local.local_dto.ImageDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 16.02.2022 at 13:42
 */
@ProvidedTypeConverter
class ImageListConverter {

    @TypeConverter
    fun imageListToJson(images: List<ImageDb>): String {
        return toJson<List<ImageDb>>(images)
    }

    @TypeConverter
    fun jsonToImageList(imageListSrc: String): List<ImageDb> {
        return fromJson(imageListSrc)
    }
}