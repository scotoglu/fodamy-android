package com.scoto.fodamy.network.models.responses

import android.os.Parcelable
import com.scoto.fodamy.network.models.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    val token: String,
    val user: User
) : Parcelable
