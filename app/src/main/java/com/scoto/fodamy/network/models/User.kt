package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val image: Image,
    val name: String,
    val username: String,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("followed_count")
    val followedCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("is_following")
    val isFollowing: Boolean,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("recipe_count")
    val recipeCount: Int,
) : Parcelable