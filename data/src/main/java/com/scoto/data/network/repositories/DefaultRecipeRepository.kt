package com.scoto.data.network.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.RecipeService
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

    override fun getEditorChoiceRecipes(): Flow<PagingData<Recipe>> {
        TODO("Not yet implemented")
    }

    override fun getLastAdded(): Flow<PagingData<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeById(recipeId: Int): Recipe =
        execute {
            recipeService.getRecipeById(recipeId).toDomainModel()
        }

    override fun getRecipeComments(recipeId: Int): Flow<PagingData<Comment>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFirstComment(recipeId: Int): Comment =
        execute {
            recipeService.getRecipeComments(recipeId, 1).data.get(0).toDomainModel()
        }

    override suspend fun sendComment(recipeId: Int, text: String): Comment =
        execute {
            recipeService.sendComment(recipeId, text).toDomainModel()
        }

    override suspend fun editComment(recipeId: Int, commentId: Int, text: String): Common {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComment(recipeId: Int, commentId: Int): Common {
        TODO("Not yet implemented")
    }

    override suspend fun likeRecipe(recipeId: Int): Common {
        TODO("Not yet implemented")
    }

    override suspend fun dislikeRecipe(recipeId: Int): Common {
        TODO("Not yet implemented")
    }

    override fun getCategoriesWithRecipes(): Flow<PagingData<Category>> {
        TODO("Not yet implemented")
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<PagingData<Recipe>> {
        TODO("Not yet implemented")
    }

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