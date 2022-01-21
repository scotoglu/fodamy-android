package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 00:15
 */
@Parcelize
data class ErrorControl(
    val code: String,
    val error: String
) : Parcelable
