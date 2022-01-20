package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 00:06
 */
@Parcelize
data class ImageList(
    val images: List<Image>
) : Parcelable
