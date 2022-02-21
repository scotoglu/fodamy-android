package com.scoto.data.local.local_dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 09:41
 */

data class ImageDb(
    val height: Int,
    val url: String,
    val width: Int
) {
    companion object {
        val EMPTY = ImageDb(0, "", 0)
    }
}
