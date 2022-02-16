package com.scoto.data.local.converters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bumptech.glide.Glide
import com.scoto.data.local.local_dto.ImageDb
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.02.2022 at 00:29
 */
@ProvidedTypeConverter
class ImageConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Downloads images as bitmap
    private fun urlToBitmap(imageDb: ImageDb): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            if (imageDb.url.isNotBlank()) {
                bitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageDb.url)
                    .submit(imageDb.width, imageDb.height).get()
            }
            return bitmap
        } catch (ex: Exception) {
            throw ex
        }
    }

    //
//    @TypeConverter
//    fun imageToByte(bitmap: Bitmap): ByteArray {
//        val outputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream)
//        return outputStream.toByteArray()
//    }
//
//    @TypeConverter
//    fun byteToImage(imageByte: ByteArray): Bitmap {
//        val inputStream = ByteArrayInputStream(imageByte)
//        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
//        return bitmap
//    }
    @TypeConverter
    fun imageDbToJson(imageDb: ImageDb): String {
        val bitmap = urlToBitmap(imageDb)
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 0, outputStream)
        val imageByte = outputStream.toByteArray()
        imageDb.imageByte = imageByte
        return toJson<ImageDb>(imageDb)
    }

    @TypeConverter
    fun jsonToImageDb(imageDbSrc: String): ImageDb {
        val imageDbObj: ImageDb = fromJson<ImageDb>(imageDbSrc)
        val inputStream = ByteArrayInputStream(imageDbObj.imageByte)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imageDbObj.image = bitmap
        return imageDbObj
    }
}
