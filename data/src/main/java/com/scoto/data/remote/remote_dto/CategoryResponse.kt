package com.scoto.data.remote.remote_dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:50
 */
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: ImageResponse?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("main_category_id")
    val mainCategoryId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("recipes")
    val recipes: List<RecipeResponse>?
)
