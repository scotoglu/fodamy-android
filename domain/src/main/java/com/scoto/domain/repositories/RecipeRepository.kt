package com.scoto.domain.repositories

import androidx.paging.PagingData
import com.scoto.domain.models.Category
import com.scoto.domain.models.CategoryDraft
import com.scoto.domain.models.Comment
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.Recipe
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.models.TimeOfRecipe
import kotlinx.coroutines.flow.Flow

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:26
 */
interface RecipeRepository {
    suspend fun getEditorChoicePaging(): Flow<PagingData<Recipe>>
    suspend fun getLastAddedPaging(): Flow<PagingData<Recipe>>
    suspend fun getRecipeCommentsPaging(recipeId: Int): Flow<PagingData<Comment>>
    suspend fun getCategoriesPaging(): Flow<PagingData<Category>>
    suspend fun getEditorChoiceRecipes(page: Int = 1): List<Recipe>
    suspend fun getLastAdded(page: Int = 1): List<Recipe>
    suspend fun getRecipeById(recipeId: Int, onlyRemote: Boolean): Recipe
    suspend fun getRecipeComments(recipeId: Int, page: Int = 1): List<Comment>
    suspend fun getFirstComment(recipeId: Int): Comment
    suspend fun sendComment(recipeId: Int, text: String)
    suspend fun editComment(recipeId: Int, commentId: Int, text: String)
    suspend fun deleteComment(recipeId: Int, commentId: Int)
    suspend fun likeRecipe(recipeId: Int)
    suspend fun dislikeRecipe(recipeId: Int)
    suspend fun getCategoriesWithRecipes(page: Int = 1): List<Category>
    suspend fun getRecipesByCategory(categoryId: Int, page: Int = 1): List<Recipe>
    suspend fun getRecipeTimes(): List<TimeOfRecipe>
    suspend fun getRecipeServing(): List<NumberOfPerson>
    suspend fun insertDraft(draft: RecipeDraft)
    suspend fun getAllDrafts(): List<RecipeDraft>
    suspend fun deleteDraft(draftId: String)
    suspend fun updateDraft(draft: RecipeDraft)
    suspend fun getAllCategories(): List<CategoryDraft>
}
