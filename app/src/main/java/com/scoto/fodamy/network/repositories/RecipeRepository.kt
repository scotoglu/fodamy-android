package com.scoto.fodamy.network.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.models.responses.BaseResponse
import com.scoto.fodamy.network.utils.CategoryPagingSource
import com.scoto.fodamy.network.utils.CommentPagingSource
import com.scoto.fodamy.network.utils.RecipePagingSource
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED
import com.scoto.fodamy.util.FROM_RECIPES_BY_CATEGORY
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecipeRepository {
    fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>>
    fun getLastAdded(): Flow<PagingData<Recipe>>
    suspend fun getRecipeById(recipeId: Int): NetworkResponse<Recipe>
    fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>>
    suspend fun getFirstComment(recipeId: Int): NetworkResponse<Comment>
    suspend fun sendComment(recipeId: Int, text: String): NetworkResponse<Comment>
    suspend fun editComment(
        recipeId: Int,
        commentId: Int,
        text: String
    ): NetworkResponse<BaseResponse>

    suspend fun deleteComment(recipeId: Int, commentId: Int): NetworkResponse<BaseResponse>
    suspend fun likeRecipe(recipeId: Int): NetworkResponse<BaseResponse>
    suspend fun dislikeRecipe(recipeId: Int): NetworkResponse<BaseResponse>
    fun getCategoriesWithRecipes(): Flow<PagingData<Category>>
    fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>>
}

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {

    override fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            RecipePagingSource(recipeService, FROM_EDITOR_CHOICE, null)
        },
    ).flow

    override fun getLastAdded(): Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RecipePagingSource(recipeService, FROM_LAST_ADDED, null) }
    ).flow

    override fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CommentPagingSource(recipeService, recipeId) }
    ).flow

    override suspend fun getFirstComment(recipeId: Int): NetworkResponse<Comment> {
        return try {
            val response = recipeService.getRecipeComments(recipeId, 1)
            val comment = response.data[0]
            NetworkResponse.Success(comment)
        } catch (ex: IndexOutOfBoundsException) {
            NetworkResponse.IndexOutOfEx(ex)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun sendComment(recipeId: Int, text: String): NetworkResponse<Comment> {
        return try {
            val response = recipeService.sendComment(recipeId, text)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun editComment(
        recipeId: Int,
        commentId: Int,
        text: String
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = recipeService.editComment(recipeId, commentId, text)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun deleteComment(
        recipeId: Int,
        commentId: Int
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = recipeService.deleteComment(recipeId, commentId)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getRecipeById(recipeId: Int): NetworkResponse<Recipe> {
        return try {
            val recipe = recipeService.getRecipeById(recipeId)
            NetworkResponse.Success(recipe)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun likeRecipe(recipeId: Int): NetworkResponse<BaseResponse> {
        return try {
            val response = recipeService.likeRecipe(recipeId)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun dislikeRecipe(recipeId: Int): NetworkResponse<BaseResponse> {
        return try {
            val response = recipeService.dislikeRecipe(recipeId)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override fun getCategoriesWithRecipes(): Flow<PagingData<Category>> =
        Pager(
            config = PagingConfig(
                pageSize = CATEGORY_NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CategoryPagingSource(recipeService)
            }
        ).flow

    override fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                RecipePagingSource(
                    recipeService,
                    FROM_RECIPES_BY_CATEGORY,
                    categoryId
                )
            }
        ).flow

    companion object {
        private const val TAG = "RecipeRepository"
        private const val NETWORK_PAGE_SIZE = 24
        private const val NETWORK_MAX_SIZE = 100
        private const val CATEGORY_NETWORK_PAGE_SIZE = 4
    }
}
