package com.scoto.data.remote.remote_dto

import com.google.gson.annotations.SerializedName

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:52
 */

data class NumberOfPersonResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String
)
