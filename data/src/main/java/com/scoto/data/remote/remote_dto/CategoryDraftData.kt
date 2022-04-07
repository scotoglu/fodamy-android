package com.scoto.data.remote.remote_dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 16.03.2022
 */
data class CategoryDraftData(
    @SerializedName("data")
    val data: List<CategoryDraftResponse>
)
