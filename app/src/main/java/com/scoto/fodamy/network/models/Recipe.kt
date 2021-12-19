package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(

    val id: Int,
    val title: String,
    val definition: String,
    val ingredients: String,
    val directions: String,
    val difference: String,
    @SerializedName("is_editor_choice")
    val isEditorChoice: Boolean,
    @SerializedName("is_liked")
    val isLiked: Boolean,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("number_of_favorite_count")
    val numberOfFavoriteCount: Int,
    @SerializedName("comment_count")
    val commentCount: Int,
    val user: User,
    @SerializedName("time_of_recipe")
    val timeOfRecipe: TimeOfRecipe,
    @SerializedName("number_of_person")
    val numberOfPerson: NumberOfPerson,
    val category: Category,
    val images: List<Image>
) : Parcelable
