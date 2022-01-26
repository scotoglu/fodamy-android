package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:42
 */
@Parcelize
data class Common(
    val code: String,
    val message: String,
    val error: String
) : Parcelable
