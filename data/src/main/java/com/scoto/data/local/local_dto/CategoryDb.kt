package com.scoto.data.local.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 09:40
 */
@Entity(tableName = "categories")
data class CategoryDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: ImageDb,
    val recipes: List<RecipeDb>
)
