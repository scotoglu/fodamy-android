package com.scoto.data.network.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.RecipeService
import com.scoto.data.network.paging_source.CategoryPagingSource
import com.scoto.data.network.paging_source.CommentPagingSource
import com.scoto.data.network.paging_source.RecipePagingSource
import com.scoto.data.utils.FROM_EDITOR_CHOICE
import com.scoto.data.utils.FROM_LAST_ADDED
import com.scoto.data.utils.FROM_RECIPES_BY_CATEGORY
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Common
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:41
 */
class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository, BaseRepository() {

    override fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>> = Pager(
        config = pageConfig,
        pagingSourceFactory = {
            RecipePagingSource(recipeService, FROM_EDITOR_CHOICE, null)
        }
    ).flow

    override fun getLastAdded(): Flow<PagingData<Recipe>> = Pager(
        config = pageConfig,
        pagingSourceFactory = {
            RecipePagingSource(recipeService, FROM_LAST_ADDED, null)
        }
    ).flow

    override suspend fun getRecipeById(recipeId: Int): Recipe =
        execute {
            recipeService.getRecipeById(recipeId).toDomainModel()
        }

    override fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>> = Pager(
        config = pageConfig,
        pagingSourceFactory = {
            CommentPagingSource(recipeService, recipeId)
        }
    ).flow

    override suspend fun getFirstComment(recipeId: Int): Comment =
        execute {
            recipeService.getRecipeComments(recipeId, 1).data.get(0).toDomainModel()
        }

    override suspend fun sendComment(recipeId: Int, text: String): Comment =
        execute {
            recipeService.sendComment(recipeId, text).toDomainModel()
        }

    override suspend fun editComment(recipeId: Int, commentId: Int, text: String): Common =
        execute {
            recipeService.editComment(recipeId, commentId, text).toDomainModel()
        }

    override suspend fun deleteComment(recipeId: Int, commentId: Int): Common =
        execute {
            recipeService.deleteComment(recipeId, commentId).toDomainModel()
        }

    override suspend fun likeRecipe(recipeId: Int): Common =
        execute {
            recipeService.likeRecipe(recipeId).toDomainModel()
        }

    override suspend fun dislikeRecipe(recipeId: Int): Common =
        execute {
            recipeService.dislikeRecipe(recipeId).toDomainModel()
        }

    override fun getCategoriesWithRecipes(): Flow<PagingData<Category>> = Pager(
        config = pageConfig,
        pagingSourceFactory = {
            CategoryPagingSource(recipeService)
        }
    ).flow

    override fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>> = Pager(
        config = pageConfig,
        pagingSourceFactory = {
            RecipePagingSource(recipeService, FROM_RECIPES_BY_CATEGORY, categoryId)
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