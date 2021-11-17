package com.scoto.fodamy.network.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Recipe


class RecipePagingSource(
    private val recipeService: RecipeService
) : PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val currentPage = params.key ?: RECIPE_STARTING_INDEX
        return try {
            val response = recipeService.getEditorChoiceRecipes(currentPage)
            val recipes = response.data
            Log.d(TAG, "load: Suc")
            LoadResult.Page(
                data = recipes,
                prevKey = if (currentPage == RECIPE_STARTING_INDEX) null else currentPage - 1,
                nextKey = if (recipes.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            Log.d(TAG, "load: Ex ")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    companion object {
        private const val TAG = "RecipePagingSource"
        private const val RECIPE_STARTING_INDEX = 1
    }
}