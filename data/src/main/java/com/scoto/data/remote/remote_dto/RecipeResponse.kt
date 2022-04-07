package com.scoto.data.remote.remote_dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:50
 */
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("category")
    val category: CategoryResponse,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("definition")
    val definition: String,
    @SerializedName("difference")
    val difference: String,
    @SerializedName("directions")
    val directions: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<ImageResponse>,
    @SerializedName("ingredients")
    val ingredients: String,
    @SerializedName("is_approved")
    val isApproved: Boolean,
    @SerializedName("is_editor_choice")
    val isEditorChoice: Boolean,
    @SerializedName("is_favorited")
    val isFavorited: Boolean,
    @SerializedName("is_liked")
    val isLiked: Boolean,
    @SerializedName("is_owner")
    val isOwner: Boolean,
    @SerializedName("language")
    val language: String?,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("number_of_favorite_count")
    val numberOfFavoriteCount: Int,
    @SerializedName("number_of_person")
    val numberOfPerson: NumberOfPersonResponse,
    @SerializedName("time_of_recipe")
    val timeOfRecipe: TimeOfRecipeResponse,
    @SerializedName("title")
    val title: String?,
    @SerializedName("user")
    val user: UserResponse,
    @SerializedName("view_count")
    val viewCount: Int?,
    @SerializedName("youtube_image")
    val youtubeImage: ImageResponse?,
    @SerializedName("youtube_url")
    val youtubeUrl: String?
)
