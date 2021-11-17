package com.scoto.fodamy.network.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Recipe
import javax.inject.Inject


interface RecipeRepository {
    suspend fun getEditorChoiceRecipes(): LiveData<PagingData<Recipe>>
}

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {

    override suspend fun getEditorChoiceRecipes(): LiveData<PagingData<Recipe>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = NETWORK_MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            RecipePagingSource(recipeService)
        },
    ).liveData


    companion object {
        private const val TAG = "RecipeRepository"
        private const val NETWORK_PAGE_SIZE = 24
        private const val NETWORK_MAX_SIZE = 100

    }
}