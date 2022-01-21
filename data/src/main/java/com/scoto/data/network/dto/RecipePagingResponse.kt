package com.scoto.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 22:13
 */
data class RecipePagingResponse(
    @SerializedName("data")
    val data: List<RecipeResponse>,
    @SerializedName("pagination")
    val pagination: PaginationResponse
)
