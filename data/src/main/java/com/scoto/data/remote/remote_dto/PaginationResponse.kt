package com.scoto.data.remote.remote_dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:55
 */

import com.google.gson.annotations.SerializedName

data class PaginationResponse(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("first_item")
    val firstItem: Int,
    @SerializedName("last_item")
    val lastItem: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int
)
