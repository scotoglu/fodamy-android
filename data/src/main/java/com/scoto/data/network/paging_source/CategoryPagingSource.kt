package com.scoto.data.network.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.RecipeService
import com.scoto.domain.models.Category

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 22:22
 */
class CategoryPagingSource(
    private val recipeService: RecipeService
) : PagingSource<Int, Category>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        val currentPage = params.key ?: CATEGORY_STARTING_INDEX
        return try {
            val response = recipeService.getCategoriesWithRecipes(currentPage)
            val categoryData = response.data.map { it.toDomainModel() }
            LoadResult.Page(
                data = categoryData,
                prevKey = if (currentPage == CATEGORY_STARTING_INDEX) null else currentPage - 1,
                nextKey = if (categoryData.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val CATEGORY_STARTING_INDEX = 1
    }
}