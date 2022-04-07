package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:35
 */
@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val definition: String,
    val ingredients: String,
    val directions: String,
    val difference: String,
    val isEditorChoice: Boolean,
    val isLiked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val user: User,
    val timeOfRecipe: TimeOfRecipe,
    val numberOfPerson: NumberOfPerson,
    val category: Category,
    val images: List<Image>
) : Parcelable
