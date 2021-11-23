package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val birthday: String,
    val cover: Image,
    val definition: String,
    val email: String,
    val gender: String,
    val image: Image,
    val name: String,
    val phone: String,
    val username: String,
    val language: String,
    val surname: String,
    val tckn: String,
    @SerializedName("cover_image")
    val coverImage: Image,
    @SerializedName("facebook_url")
    val facebookUrl: String,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("followed_count")
    val followedCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("instagram_url")
    val instagramUrl: String,
    @SerializedName("is_banned")
    val isBanned: Int,
    @SerializedName("is_following")
    val isFollowing: Boolean,
    @SerializedName("is_top_user_choice")
    val isTopUserChoice: Boolean,
    @SerializedName("is_trusted")
    val isTrusted: Int,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("recipe_count")
    val recipeCount: Int,
    @SerializedName("top_user")
    val topUser: String,
    @SerializedName("twitter_url")
    val twitterUrl: String,
    @SerializedName("youtube_url")
    val youtubeUrl: String
) : Parcelable