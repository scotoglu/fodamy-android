package com.scoto.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:48
 */
data class AuthResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserResponse
)
