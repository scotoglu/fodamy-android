package com.scoto.data.remote.remote_dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 22:14
 */
data class CommentPagingResponse(
    @SerializedName("data")
    val data: List<CommentResponse>,
    @SerializedName("pagination")
    val pagination: PaginationResponse
)
