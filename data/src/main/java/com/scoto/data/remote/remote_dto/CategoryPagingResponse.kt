package com.scoto.data.remote.remote_dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 22:15
 */
data class CategoryPagingResponse(
    @SerializedName("data")
    val data: List<CategoryResponse>,
    @SerializedName("pagination")
    val pagination: PaginationResponse
)
