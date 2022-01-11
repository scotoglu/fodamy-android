package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.api.AuthService
import com.scoto.fodamy.network.models.responses.AuthResponse
import com.scoto.fodamy.network.models.responses.BaseResponse
import javax.inject.Inject

interface AuthRepository {
    suspend fun register(username: String,email: String, password: String): NetworkResponse<AuthResponse>
    suspend fun forgot(email: String): NetworkResponse<AuthResponse>
    suspend fun logout(): NetworkResponse<BaseResponse<Any>>
    suspend fun login(username: String, password: String): NetworkResponse<AuthResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStoreManager: DataStoreManager
) : AuthRepository, BaseRepositoryImpl() {

    override suspend fun login(
        username: String,
        password: String
    ): NetworkResponse<AuthResponse> =
        execute {
            val response = authService.login(username, password)
            saveUserId(response.user.id)
            saveAuth(response.token)
            response
        }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): NetworkResponse<AuthResponse> =
        execute {
            authService.register(username, email, password)
        }

    override suspend fun forgot(email: String): NetworkResponse<AuthResponse> =
        execute {
            authService.forgot(email)
        }

    override suspend fun logout(): NetworkResponse<BaseResponse<Any>> = execute {
        removeUserId()
        removeAuth()
        authService.logout()
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
        dataStoreManager.removeToken()
    }
}
