package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:30
 */
@Parcelize
data class User(
    val id: Int,
    val image: Image,
    val name: String,
    val username: String,
    val favoritesCount: String,
    val followedCount: String,
    val followingCount: String,
    val isFollowing: Boolean,
    val likesCount: String,
    val recipeCount: String
) : Parcelable
