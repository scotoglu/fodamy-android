package com.scoto.fodamy.network.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


interface RecipeRepository {
    suspend fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>>
    suspend fun getLastAdded(): Flow<PagingData<Recipe>>
    suspend fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>>
    suspend fun getFirstComment(recipeId: Int): NetworkResult<Comment>
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

    override suspend fun getFirstComment(recipeId: Int): NetworkResult<Comment> {
        return try {
            val response = recipeService.getRecipeComments(recipeId, 1)
            val comment = response.data[0]
            NetworkResult.Success(comment)
        } catch (e: IOException) {
            NetworkResult.Error.IOError(e.message)
        } catch (e: HttpException) {
            NetworkResult.Error.HttpError(e.message())
        } catch (e: IndexOutOfBoundsException) {
            NetworkResult.Error.OutOfIndexError(e.message.toString())
        }
    }

    companion object {
        private const val TAG = "RecipeRepository"
        private const val NETWORK_PAGE_SIZE = 24
        private const val NETWORK_MAX_SIZE = 100

    }
}