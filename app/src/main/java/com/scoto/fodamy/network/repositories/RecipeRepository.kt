package com.scoto.fodamy.network.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface RecipeRepository {
    suspend fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>>
    suspend fun getLastAdded(): Flow<PagingData<Recipe>>
}

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

    companion object {
        private const val TAG = "RecipeRepository"
        private const val NETWORK_PAGE_SIZE = 24
        private const val NETWORK_MAX_SIZE = 100

    }
}