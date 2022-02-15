package com.scoto.data.local.local_dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 09:41
 */
@Entity(tableName = "users")
data class UserDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "image_id")
    val imageId: Int,
    val name: String,
    val username: String,
    @ColumnInfo(name = "favorites_count")
    val favoritesCount: Int,
    @ColumnInfo(name = "followed_count")
    val followedCount: Int,
    @ColumnInfo(name = "following_count")
    val followingCount: Int,
    @ColumnInfo(name = "is_following")
    val isFollowing: Boolean,
    @ColumnInfo(name = "likes_count")
    val likesCount: Int,
    @ColumnInfo(name = "recipe_count")
    val recipeCount: Int
)
