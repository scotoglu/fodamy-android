package com.scoto.data.local.local_dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 09:41
 */
@Entity(tableName = "user")
data class UserDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val username: String,
    val favoritesCount: Int,
    val followedCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
    val likesCount: Int,
    val recipeCount: Int,
    @Embedded
    val image: ImageDb
)
