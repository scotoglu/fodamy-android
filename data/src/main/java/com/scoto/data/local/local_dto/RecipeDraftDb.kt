package com.scoto.data.local.local_dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@Entity(tableName = "recipe_drafts")
data class RecipeDraftDb(
    @PrimaryKey
    val id: String,
    val title: String,
    val ingredients: String,
    val direction: String,
    @Embedded("category")
    val category: CategoryDraftDb,
    @Embedded("number_of_person")
    val numberOfPerson: NumberOfPersonDb,
    @Embedded("time_of_recipe")
    val timeOfRecipe: TimeOfRecipeDb,
    val images: List<String>
)
