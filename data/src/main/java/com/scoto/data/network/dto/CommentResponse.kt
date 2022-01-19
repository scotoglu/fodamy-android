package com.scoto.data.network.dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:20
 */
import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("difference")
    val difference: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: UserResponse
)