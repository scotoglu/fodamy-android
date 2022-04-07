package com.scoto.domain.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:33
 */
@Parcelize
data class Image(
    val height: Int,
    val url: String,
    val width: Int,
    val image: Bitmap? = null
) : Parcelable
