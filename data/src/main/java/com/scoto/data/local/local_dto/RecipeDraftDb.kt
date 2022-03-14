package com.scoto.data.local.local_dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@Entity(tableName = "recipe_drafts")
data class RecipeDraftDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val ingredients: String,
    val direction: String,
    @ColumnInfo("category_id")
    val categoryId: Int,
    @ColumnInfo("number_of_person_id")
    val numberOfPersonId: Int,
    @ColumnInfo("time_of_recipe_id")
    val timeOfRecipeId: Int,
    val images: List<String>
)
