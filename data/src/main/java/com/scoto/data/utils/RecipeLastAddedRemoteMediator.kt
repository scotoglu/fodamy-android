package com.scoto.data.utils

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.scoto.data.local.dao.RecipeDao
import com.scoto.data.local.dao.RemoteKeysDao
import com.scoto.data.local.local_dto.RecipeDb
import com.scoto.data.local.local_dto.RemoteKeysLast
import com.scoto.data.mapper.toLocalDto
import com.scoto.data.remote.services.RecipeService

/**
 * @author Sefa ÇOTOĞLU
 * Created 24.02.2022 at 22:56
 */
@ExperimentalPagingApi
class RecipeLastAddedRemoteMediator(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val keyProvider: RemoteKeyProvider
) : RemoteMediator<Int, RecipeDb>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeDb>
    ): MediatorResult {

        keyProvider.setKeyType(PagingKeyType.LAST_ADDED)

        return try {
            val currentPage = when (val keyData = keyProvider.getCurrentPage(loadType, state)) {
                is MediatorResult.Success -> {
                    return keyData
                }
                else -> {
                    keyData as Int
                }
            }

            val response = recipeService.getLastAddedRecipes(currentPage)
            val endOfPagination = response.data.isEmpty()

            val prevPage = if (currentPage == STARTING_INDEX) null else currentPage - 1
            val nextPage = if (endOfPagination) null else currentPage + 1

            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.deleteLastKeys()
            }
            val keys = response.data.map {
                RemoteKeysLast(
                    id = it.id,
                    prev = prevPage,
                    next = nextPage
                )
            }
            remoteKeysDao.insertLasAtAddedRemoteKeys(keys)
            recipeDao.insertRecipes(response.data.map { it.toLocalDto(isLastAdded = true) })
            MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (ex: Exception) {
            MediatorResult.Error(ex)
        }
    }

    companion object {
        private const val STARTING_INDEX = 1
    }
}
