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
    suspend fun editComment(recipeId: Int, commentId: Int, text: String): NetworkResponse<BaseResponse<Any>>
    suspend fun deleteComment(recipeId: Int, commentId: Int): NetworkResponse<BaseResponse<Any>>
    suspend fun likeRecipe(recipeId: Int): NetworkResponse<BaseResponse<Any>>
    suspend fun dislikeRecipe(recipeId: Int): NetworkResponse<BaseResponse<Any>>
    fun getCategoriesWithRecipes(): Flow<PagingData<Category>>
    fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>>
}

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository, BaseRepositoryImpl() {

    override fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>> = Pager(
        config = pageConfig,
        pagingSourceFactory = {
            RecipePagingSource(recipeService, FROM_EDITOR_CHOICE, null)
        },
    ).flow

    override fun getLastAdded(): Flow<PagingData<Recipe>> = Pager(
        config = pageConfig,
        pagingSourceFactory = { RecipePagingSource(recipeService, FROM_LAST_ADDED, null) }
    ).flow

    override fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>> = Pager(
        config = pageConfig,
        pagingSourceFactory = { CommentPagingSource(recipeService, recipeId) }
    ).flow

    override suspend fun getFirstComment(recipeId: Int): NetworkResponse<Comment> = execute {
        val response = recipeService.getRecipeComments(recipeId, 1)
        val comment = response.data[0]
        comment
    }

    override suspend fun sendComment(recipeId: Int, text: String): NetworkResponse<Comment> =
        execute {
            recipeService.sendComment(recipeId, text)
        }

    override suspend fun editComment(
        recipeId: Int,
        commentId: Int,
        text: String
    ): NetworkResponse<BaseResponse<Any>> = execute {
        recipeService.editComment(recipeId, commentId, text)
    }

    override suspend fun deleteComment(
        recipeId: Int,
        commentId: Int
    ): NetworkResponse<BaseResponse<Any>> = execute {
        recipeService.deleteComment(recipeId, commentId)
    }

    override suspend fun getRecipeById(recipeId: Int): NetworkResponse<Recipe> =
        execute {
            recipeService.getRecipeById(recipeId)
        }

    override suspend fun likeRecipe(recipeId: Int): NetworkResponse<BaseResponse<Any>> =
        execute {
            recipeService.likeRecipe(recipeId)
        }

    override suspend fun dislikeRecipe(recipeId: Int): NetworkResponse<BaseResponse<Any>> =
        execute {
            recipeService.dislikeRecipe(recipeId)
        }

    override fun getCategoriesWithRecipes(): Flow<PagingData<Category>> =
        Pager(
            config = pageConfig,
            pagingSourceFactory = {
                CategoryPagingSource(recipeService)
            }
        ).flow

    override fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>> =
        Pager(
            config = pageConfig,
            pagingSourceFactory = {
                RecipePagingSource(
                    recipeService,
                    FROM_RECIPES_BY_CATEGORY,
                    categoryId
                )
            }
        ).flow

    companion object {
        private const val NETWORK_PAGE_SIZE = 24
        private const val NETWORK_MAX_SIZE = 100
        private const val CATEGORY_NETWORK_PAGE_SIZE = 4
        private val pageConfig = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        )
    }
}
