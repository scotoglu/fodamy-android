package com.scoto.data.network.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.RecipeService
import com.scoto.data.utils.FROM_EDITOR_CHOICE
import com.scoto.data.utils.FROM_LAST_ADDED
import com.scoto.data.utils.FROM_RECIPES_BY_CATEGORY
import com.scoto.domain.models.Recipe

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 22:09
 */
class RecipePagingSource(
    private val recipeService: RecipeService,
    private val fetchedFrom: String,
    private val categoryId: Int?
) : PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val currentPage = params.key ?: RECIPE_STARTING_INDEX

        return try {
            val response = when (fetchedFrom) {
                FROM_EDITOR_CHOICE -> {
                    recipeService.getEditorChoiceRecipes(currentPage)
                }
                FROM_LAST_ADDED -> {
                    recipeService.getLastAddedRecipes(currentPage)
                }
                FROM_RECIPES_BY_CATEGORY -> {
                    if (categoryId != null) {
                        recipeService.getRecipesByCategory(
                            categoryId,
                            currentPage
                        )
                    } else null
                }
                else -> {
                    recipeService.getEditorChoiceRecipes(currentPage)
                }
            }

            val recipes = response?.toDomainModel()?.data ?: emptyList()
            LoadResult.Page(
                data = recipes,
                prevKey = if (currentPage == RECIPE_STARTING_INDEX) null else currentPage - 1,
                nextKey = if (recipes.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
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
        private const val RECIPE_STARTING_INDEX = 1
    }
}