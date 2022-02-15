package com.scoto.data.local.dao

import androidx.room.Dao

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:49
 */
@Dao
interface RecipeDao {
    fun getRecipeById(recipeId: Int)
    fun setRecipes()
    fun getCategories()
    fun getCategoryById(categoryId: Int)
    fun getComments(recipeId: Int)
}
