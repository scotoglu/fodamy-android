package com.scoto.domain.utils

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 09:53
 */
interface DataStoreManager {
    suspend fun isLogin(): Boolean
    suspend fun isUserComment(commentUserId: Int): Boolean
    suspend fun isLoginLiveData(): LiveData<String>
    suspend fun isFirstTimeLaunch(): Boolean
    val token: Flow<String>
    suspend fun saveFirstTimeLaunched()
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun removeToken()
    suspend fun saveUserId(userId: Int)
    suspend fun getUserId(): Int
    suspend fun removeUserId()
}