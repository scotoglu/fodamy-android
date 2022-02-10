package com.scoto.data.remote.remote_dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:52
 */
import com.google.gson.annotations.SerializedName

data class TimeOfRecipeResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String
)
