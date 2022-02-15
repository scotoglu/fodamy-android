package com.scoto.data.local.local_dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.TimeOfRecipe

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:50
 */
@Entity(tableName = "recipes")
data class RecipeDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val definition: String,
    val ingredients: String,
    val directions: String,
    val difference: String,
    @ColumnInfo(name = "is_editor_choice")
    val isEditorChoice: Boolean,
    @ColumnInfo(name = "is_liked")
    val isLiked: Boolean,
    @ColumnInfo(name = "like_count")
    val likeCount: Int,
    @ColumnInfo(name = "comment_count")
    val commentCount: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @Embedded
    val timeOfRecipe: TimeOfRecipe,
    @Embedded
    val numberOfPerson: NumberOfPerson,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "image_id")
    val imageId: Int
)
