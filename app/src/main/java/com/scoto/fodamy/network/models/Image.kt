package com.scoto.fodamy.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val height: Int,
    val key: String,
    val order: Int,
    val url: String,
    val width: Int
) : Parcelable

@Parcelize
data class ImageList(
    val images: List<Image>
) : Parcelable