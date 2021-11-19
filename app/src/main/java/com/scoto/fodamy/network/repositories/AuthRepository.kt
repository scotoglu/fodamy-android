package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.api.AuthService
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(username: String, password: String): NetworkResult<String>
    suspend fun register(username: String, email: String, password: String): NetworkResult<String>
    suspend fun forgot(email: String): NetworkResult<String>
    suspend fun logout(): NetworkResult<String>
}


class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {


    override suspend fun login(username: String, password: String): NetworkResult<String> {
        val response = authService.login(username, password)
        return if (response.isSuccessful) {
            val token = response.body()?.token
            token?.let { saveAuth(it) }
            NetworkResult.Success("Success")
        } else {
            NetworkResult.Error(response.errorBody())
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): NetworkResult<String> {
        val response = authService.register(username, email, password)
        return if (response.isSuccessful) {
            NetworkResult.Success("Success")
        } else {
            NetworkResult.Error(response.errorBody())
        }
    }

    override suspend fun forgot(email: String): NetworkResult<String> {
        val response = authService.forgot(email)
        return if (response.isSuccessful) {
            NetworkResult.Success("Success")
        } else {
            NetworkResult.Error(response.errorBody())
        }
    }


    override suspend fun logout(): NetworkResult<String> {
        val response = authService.logout()
        return if (response.isSuccessful) {
            removeAuth()
            NetworkResult.Success(response.message())
        } else {
            NetworkResult.Error(response.errorBody())
        }
    }

    private suspend fun saveAuth(token: String) {
        dataStoreManager.saveToken(token)
    }

    private suspend fun removeAuth() {
        dataStoreManager.removeAuth()
    }
}