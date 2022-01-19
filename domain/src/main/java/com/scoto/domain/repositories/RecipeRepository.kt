package com.scoto.domain.repositories

import androidx.paging.PagingData
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Common
import com.scoto.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:26
 */
interface RecipeRepository {
    fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>>
    fun getLastAdded(): Flow<PagingData<Recipe>>
    suspend fun getRecipeById(recipeId: Int): Recipe
    fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>>
    suspend fun getFirstComment(recipeId: Int): Comment
    suspend fun sendComment(recipeId: Int, text: String): Comment
    suspend fun editComment(recipeId: Int, commentId: Int, text: String): Common
    suspend fun deleteComment(recipeId: Int, commentId: Int):Common
    suspend fun likeRecipe(recipeId: Int): Common
    suspend fun dislikeRecipe(recipeId: Int): Common
    fun getCategoriesWithRecipes(): Flow<PagingData<Category>>
    fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>>
}