package com.scoto.fodamy.network.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.utils.CommentPagingSource
import com.scoto.fodamy.network.utils.RecipePagingSource
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface RecipeRepository {
    suspend fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>>
    suspend fun getLastAdded(): Flow<PagingData<Recipe>>
    suspend fun getRecipeById(recipeId: Int): NetworkResponse<Recipe>
    suspend fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>>
    suspend fun getFirstComment(recipeId: Int): NetworkResponse<Comment>
}

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {

    override suspend fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            RecipePagingSource(recipeService, FROM_EDITOR_CHOICE)
        },
    ).flow

    override suspend fun getLastAdded(): Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RecipePagingSource(recipeService, FROM_LAST_ADDED) }
    ).flow


    override suspend fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>> = Pager(
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

    companion object {
        private const val TAG = "RecipeRepository"
        private const val NETWORK_PAGE_SIZE = 24
        private const val NETWORK_MAX_SIZE = 100

    }
}