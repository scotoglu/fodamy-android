package com.scoto.domain.models

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
data class RecipeDraft(
    val title: String,
    val ingredients: String,
    val direction: String,
    val categoryId: Int,
    val numberOfPersonId: Int,
    val timeOfRecipeId: Int,
    val image: List<String>
)
