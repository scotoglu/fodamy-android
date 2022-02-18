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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageDb

        if (height != other.height) return false
        if (url != other.url) return false
        if (image != other.image) return false
        if (imageByte != null) {
            if (other.imageByte == null) return false
            if (!imageByte.contentEquals(other.imageByte)) return false
        } else if (other.imageByte != null) return false
        if (width != other.width) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + url.hashCode()
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (imageByte?.contentHashCode() ?: 0)
        result = 31 * result + width
        return result
    }
}
