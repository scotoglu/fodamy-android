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

    private fun urlToBitmap(imageDb: ImageDb): Bitmap {
        return Glide.with(context)
            .asBitmap()
            .load(imageDb.url)
            .submit(imageDb.width, imageDb.height).get()
    }

    @TypeConverter
    fun imageToByte(imageDb: ImageDb): ByteArray {
        val bitmap = urlToBitmap(imageDb)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun byteToImage(imageByte: ByteArray): ImageDb {
        val inputStream = ByteArrayInputStream(imageByte)
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        return ImageDb(0, "", bitmap, 0)
    }
}
