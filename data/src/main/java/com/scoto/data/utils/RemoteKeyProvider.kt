package com.scoto.data.utils

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.scoto.data.local.dao.RemoteKeysDao
import com.scoto.data.local.local_dto.RecipeDb
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 24.02.2022 at 23:56
 */

@ExperimentalPagingApi
class RemoteKeyProvider @Inject constructor(
    private val remoteKeysDao: RemoteKeysDao,
) {
    private var keyType: PagingKeyType? = null

    fun setKeyType(keyType: PagingKeyType) {
        this.keyType = keyType
    }

    fun getCurrentPage(loadType: LoadType, state: PagingState<Int, RecipeDb>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.next?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                remoteKeys?.prev ?: RemoteMediator.MediatorResult.Success(remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage =
                    remoteKeys?.next ?: RemoteMediator.MediatorResult.Success(remoteKeys != null)
                nextPage
            }
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RecipeDb>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                when (keyType) {
                    PagingKeyType.EDITOR_CHOICE -> {
                        remoteKeysDao.getEditorRemoteKeys(id)
                    }
                    PagingKeyType.LAST_ADDED -> {
                        remoteKeysDao.getLastAddedRemoteKeys(id)
                    }
                    else -> {
                        remoteKeysDao.getEditorRemoteKeys(id)
                    }
                }
            }
        }
    }

    private fun getRemoteKeysForFirstItem(
        state: PagingState<Int, RecipeDb>
    ): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { recipe ->
                when (keyType) {
                    PagingKeyType.EDITOR_CHOICE -> {
                        remoteKeysDao.getEditorRemoteKeys(recipe.id)
                    }
                    PagingKeyType.LAST_ADDED -> {
                        remoteKeysDao.getLastAddedRemoteKeys(recipe.id)
                    }
                    else -> {
                        remoteKeysDao.getEditorRemoteKeys(recipe.id)
                    }
                }
            }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, RecipeDb>
    ): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { recipe ->
            when (keyType) {
                PagingKeyType.EDITOR_CHOICE -> {
                    remoteKeysDao.getEditorRemoteKeys(recipe.id)
                }
                PagingKeyType.LAST_ADDED -> {
                    remoteKeysDao.getLastAddedRemoteKeys(recipe.id)
                }
                else -> {
                    remoteKeysDao.getEditorRemoteKeys(recipe.id)
                }
            }
        }
    }
}
