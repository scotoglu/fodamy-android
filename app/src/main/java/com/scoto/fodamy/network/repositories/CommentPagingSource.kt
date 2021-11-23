package com.scoto.fodamy.network.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scoto.fodamy.network.api.RecipeService
import com.scoto.fodamy.network.models.Comment

class CommentPagingSource(
    private val recipeService: RecipeService,
    private val recipeId: Int
) : PagingSource<Int, Comment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        val currentPage = params.key ?: COMMENT_STARTING_INDEX
        return try {
            val response = recipeService.getRecipeComments(recipeId,currentPage)
            val comment = response.data
            LoadResult.Page(
                data = comment,
                prevKey = if (currentPage == COMMENT_STARTING_INDEX) null else currentPage - 1,
                nextKey = if (comment.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val COMMENT_STARTING_INDEX = 1
    }
}