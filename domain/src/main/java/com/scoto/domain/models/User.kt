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
    val image: Image?,
    val name: String,
    val username: String,
    val favoritesCount: Int,
    val followedCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
    val likesCount: Int,
    val recipeCount: Int
) : Parcelable
