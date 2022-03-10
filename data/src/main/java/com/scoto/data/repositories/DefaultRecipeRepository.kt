package com.scoto.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.scoto.data.di.ApplicationScope
import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.RemoteKeysDao
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.mapper.toLocalDto
import com.scoto.data.remote.services.RecipeService
import com.scoto.data.utils.CommentsRemoteMediator
import com.scoto.data.utils.RecipeEditorRemoteMediator
import com.scoto.data.utils.RecipeLastAddedRemoteMediator
import com.scoto.data.utils.RemoteMediatorCategories
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:41
 */
@ExperimentalPagingApi
class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao,
    private val remoteKeysDao: RemoteKeysDao,
    @ApplicationScope private val scope: CoroutineScope
) : RecipeRepository, BaseRepository() {

    private val pageConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        maxSize = MAX_SIZE,
        enablePlaceholders = false
    )

    override suspend fun getEditorChoicePaging(): Flow<PagingData<Recipe>> =
        execute {
            val pagingSourceFactory = { recipeDao.getEditorChoicesPaging() }
            Pager(
                config = pageConfig,
                remoteMediator = RecipeEditorRemoteMediator(
                    recipeService = recipeService,
                    recipeDao = recipeDao,
                    remoteKeysDao = remoteKeysDao
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow.map { pagingData ->
                pagingData.map {
                    it.toDomainModel()
                }
            }
        }

    override suspend fun getLastAddedPaging(): Flow<PagingData<Recipe>> =
        execute {
            val pagingSourceFactory = { recipeDao.getLastAddedPaging() }
            Pager(
                config = pageConfig,
                remoteMediator = RecipeLastAddedRemoteMediator(
                    recipeService = recipeService,
                    recipeDao = recipeDao,
                    remoteKeysDao = remoteKeysDao
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow.map { pagingData ->
                pagingData.map {
                    it.toDomainModel()
                }
            }
        }

    override suspend fun getRecipeCommentsPaging(recipeId: Int): Flow<PagingData<Comment>> =
        execute {
            val pagingSourceFactory = { recipeDao.getRecipeCommentsPaging(recipeId) }
            Pager(
                config = pageConfig,
                remoteMediator = CommentsRemoteMediator(
                    recipeService = recipeService,
                    recipeDao = recipeDao,
                    remoteKeysDao = remoteKeysDao,
                    recipeId = recipeId
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow.map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
        }

    override suspend fun getEditorChoiceRecipes(page: Int): List<Recipe> =
        execute {
            val local = fetchFromLocal { recipeDao.getEditorChoices().map { it.toDomainModel() } }
            if (local?.isNotEmpty() == true) {
                local
            } else {
                val remote = recipeService.getEditorChoiceRecipes(page).data
                saveToLocal { recipeDao.insertRecipes(remote.map { it.toLocalDto() }) }
                remote.map { it.toDomainModel() }
            }
        }

    override suspend fun getLastAdded(page: Int): List<Recipe> =
        execute {
            val local = fetchFromLocal { recipeDao.getLastAdded().map { it.toDomainModel() } }
            if (local?.isNotEmpty() == true) {
                local
            } else {
                val remote = recipeService.getLastAddedRecipes(page).data
                saveToLocal { recipeDao.insertRecipes(remote.map { it.toLocalDto(isLastAdded = true) }) }
                remote.map { it.toDomainModel() }
            }
        }

    override suspend fun getRecipeById(recipeId: Int, onlyRemote: Boolean): Recipe =
        execute {
            if (onlyRemote) {
                recipeService.getRecipeById(recipeId).toDomainModel()
            } else {
                fetchFromLocal { recipeDao.getRecipeDetails(recipeId).toDomainModel() }!!
            }
        }

    override suspend fun getRecipeComments(recipeId: Int, page: Int): List<Comment> =
        execute {
            val local =
                fetchFromLocal { recipeDao.getRecipeComments(recipeId).map { it.toDomainModel() } }
            if (local?.isNotEmpty() == true) {
                local
            } else {
                val remote = recipeService.getRecipeComments(recipeId, page).data
                saveToLocal { recipeDao.insertComments(remote.map { it.toLocalDto(recipeId) }) }
                remote.map { it.toDomainModel() }
            }
        }

    override suspend fun getFirstComment(recipeId: Int): Comment =
        execute {
            val local =
                fetchFromLocal { recipeDao.getFirstRecipeComments(recipeId).toDomainModel() }
            local ?: recipeService.getRecipeComments(recipeId, 1).data[0].toDomainModel()
        }

    override suspend fun sendComment(recipeId: Int, text: String): Unit =
        execute {
            recipeService.sendComment(recipeId, text)
        }

    override suspend fun editComment(recipeId: Int, commentId: Int, text: String): Unit =
        execute {
            recipeService.editComment(recipeId, commentId, text)
        }

    override suspend fun deleteComment(recipeId: Int, commentId: Int): Unit =
        execute {
            recipeService.deleteComment(recipeId, commentId)
            recipeDao.deleteComment(commentId)
        }

    override suspend fun likeRecipe(recipeId: Int): Unit =
        execute {
            recipeService.likeRecipe(recipeId)
        }

    override suspend fun dislikeRecipe(recipeId: Int): Unit =
        execute {
            recipeService.dislikeRecipe(recipeId)
        }

    override suspend fun getCategoriesPaging(): Flow<PagingData<Category>> =
        execute {
            val pagingSourceFactory = { recipeDao.getCategoriesPaging() }
            Pager(
                config = pageConfig,
                remoteMediator = RemoteMediatorCategories(
                    recipeService = recipeService,
                    recipeDao = recipeDao,
                    remoteKeysDao = remoteKeysDao
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow.map { pagingData ->
                pagingData.filter {
                    // Some categories has no recipes
                    it.recipes.isNotEmpty()
                }.map {
                    it.toDomainModel()
                }
            }
        }

    override suspend fun getCategoriesWithRecipes(page: Int): List<Category> =
        execute {
            val local = fetchFromLocal { recipeDao.getCategories().map { it.toDomainModel() } }
            if (local?.isNotEmpty() == true) {
                local
            } else {
                val remote = recipeService.getCategoriesWithRecipes(page).data
                saveToLocal {
                    recipeDao.insertCategories(
                        remote.filter {
                            it.recipes?.size!! > 0
                        }.map { it.toLocalDto() }
                    )
                }
                remote.map { it.toDomainModel() }
                    .filter { it.recipes?.size!! > 0 }
            }
        }

    override suspend fun getRecipesByCategory(categoryId: Int, page: Int): List<Recipe> =
        execute {
            fetchFromLocal { recipeDao.getRecipesByCategory(categoryId).recipes.map { it.toDomainModel() } }
                ?: recipeService.getRecipesByCategory(
                    categoryId,
                    page
                ).data.map { it.toDomainModel() }
        }

    companion object {
        private const val PAGE_SIZE = 24
        private const val MAX_SIZE = 100
    }
}
