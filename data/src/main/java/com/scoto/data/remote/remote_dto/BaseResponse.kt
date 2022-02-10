package com.scoto.data.remote.remote_dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:27
 */
data class BaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("pagination")
    val pagination: PaginationResponse
)
