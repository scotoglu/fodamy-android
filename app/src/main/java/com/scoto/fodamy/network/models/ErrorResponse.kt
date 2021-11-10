package com.scoto.fodamy.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(
    val code: String,
    val error: String
) : Parcelable