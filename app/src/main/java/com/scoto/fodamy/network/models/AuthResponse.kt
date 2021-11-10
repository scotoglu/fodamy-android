package com.scoto.fodamy.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    val token: String,
    val user: User
) : Parcelable
