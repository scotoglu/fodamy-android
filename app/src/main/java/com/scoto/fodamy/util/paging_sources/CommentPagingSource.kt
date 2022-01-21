package com.scoto.fodamy.util.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scoto.domain.models.Comment
import com.scoto.domain.repositories.RecipeRepository
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 22:24
 */
class CommentPagingSource @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeId: Int
) : PagingSource<Int, Comment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        val currentPage = params.key ?: COMMENT_STARTING_INDEX
        return try {
            val response = recipeRepository.getRecipeComments(recipeId,currentPage)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == COMMENT_STARTING_INDEX) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
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