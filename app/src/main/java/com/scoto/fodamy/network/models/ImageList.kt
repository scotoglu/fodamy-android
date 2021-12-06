package com.scoto.fodamy.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageList(
    val images: List<Image>
) : Parcelable
