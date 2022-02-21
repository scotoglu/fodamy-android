package com.scoto.data.local.local_dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 10:21
 */
@Entity(tableName = "comments")
data class CommentDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val text: String,
    val user: UserDb,
    val difference: String,
    @ColumnInfo(name = "recipe_id")
    val recipeId: Int
)
