package com.scoto.data.remote.remote_dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:48
 */
data class ImageResponse(
    @SerializedName("height")
    val height: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)
