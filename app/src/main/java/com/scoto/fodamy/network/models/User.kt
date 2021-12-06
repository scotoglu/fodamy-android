package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val cover: Image,
    val definition: String,
    val email: String,
    val image: Image,
    val name: String,
    val username: String,
    val surname: String,
    @SerializedName("cover_image")
    val coverImage: Image,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("followed_count")
    val followedCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("is_following")
    val isFollowing: Boolean,
    @SerializedName("is_top_user_choice")
    val isTopUserChoice: Boolean,
    @SerializedName("is_trusted")
    val likesCount: Int,
    @SerializedName("recipe_count")
    val recipeCount: Int,
) : Parcelable