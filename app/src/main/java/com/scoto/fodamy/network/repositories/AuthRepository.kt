package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.api.AuthService
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(username: String, password: String): NetworkResult<String>
    suspend fun register(username: String, email: String, password: String): NetworkResult<String>
}


class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun login(username: String, password: String): NetworkResult<String> {
        val response = authService.login(username, password)
        return if (response.isSuccessful) {
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
}