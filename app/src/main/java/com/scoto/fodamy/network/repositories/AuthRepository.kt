package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.api.AuthService
import com.scoto.fodamy.network.models.responses.BaseResponse
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(username: String, password: String): NetworkResponse<String>
    suspend fun register(username: String, email: String, password: String): NetworkResponse<String>
    suspend fun forgot(email: String): NetworkResponse<String>
    suspend fun logout(): NetworkResponse<BaseResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {
    //

    override suspend fun login(username: String, password: String): NetworkResponse<String> {
        return try {
            val response = authService.login(username, password)
            val token = response.token
            val userID = response.user.id
            saveUserId(userID)
            saveAuth(token)
            NetworkResponse.Success("response")
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): NetworkResponse<String> {
        return try {
            authService.register(username, email, password)
            NetworkResponse.Success("")
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun forgot(email: String): NetworkResponse<String> {
        return try {
            authService.forgot(email)
            NetworkResponse.Success("")
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun logout(): NetworkResponse<BaseResponse> {
        return try {
            val response = authService.logout()
            removeUserId()
            removeAuth()
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    private suspend fun saveUserId(userId: Int) {
        dataStoreManager.saveUserId(userId)
    }

    private suspend fun removeUserId() {
        dataStoreManager.removeUserId()
    }

    private suspend fun saveAuth(token: String) {
        dataStoreManager.saveToken(token)
    }

    private suspend fun removeAuth() {
        dataStoreManager.removeAuth()
    }
}
