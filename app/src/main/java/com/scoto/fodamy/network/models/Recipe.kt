package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(

    val id: Int,
    val title: String,
    val definition: String,
    val language: String,
    val ingredients: String,
    val directions: String,
    @SerializedName("youtube_url")
    val youtubeUrl: String,
    @SerializedName("youtube_image")
    val youtubeImage: Image,
    val difference: String,
    @SerializedName("is_editor_choice")
    val isEditorChoice: Boolean,
    @SerializedName("is_owner")
    val isOwner: Boolean,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("number_of_favorite_count")
    val numberOfFavoriteCount: Int,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("is_approved")
    val isApproved: Boolean,
    val user: User,
    @SerializedName("time_of_recipe")
    val timeOfRecipe: TimeOfRecipe,
    @SerializedName("number_of_person")
    val numberOfPerson: NumberOfPerson,
    val category: Category,
    val images: List<Image>
) : Parcelable

