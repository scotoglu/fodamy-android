package com.scoto.data.local.local_dto

import android.graphics.Bitmap

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 09:41
 */

data class ImageDb(
    val height: Int,
    val url: String,
    var image: Bitmap? = null,
    var imageByte: ByteArray? = null,
    val width: Int
) {
    companion object {
        val EMPTY = ImageDb(0, "", null, null, 0)
    }
}
