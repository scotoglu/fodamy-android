package com.scoto.data.local.local_dto

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 09:41
 */
@Entity(tableName = "images")
data class ImageDb(
    val height: Int,
    val url: String,
    val image: Bitmap?,
    val width: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
