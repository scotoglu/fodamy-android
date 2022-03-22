package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@Parcelize
data class RecipeDraft(
    val id: String,
    val title: String,
    val ingredients: String,
    val direction: String,
    val category: CategoryDraft,
    val numberOfPerson: NumberOfPerson,
    val timeOfRecipe: TimeOfRecipe,
    var image: List<String>
) : Parcelable
