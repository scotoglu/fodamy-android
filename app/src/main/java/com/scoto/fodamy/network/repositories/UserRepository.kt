package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.api.UserService
import com.scoto.fodamy.network.models.responses.BaseResponse
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


interface UserRepository {
    suspend fun followUser(followedId: Int): NetworkResponse<BaseResponse>

    suspend fun unFollowUser(followedId: Int): NetworkResponse<BaseResponse>
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun followUser(followedId: Int): NetworkResponse<BaseResponse> {
        return try {
            val response = userService.followUser(followedId)
            NetworkResponse.Success(response)
        } catch (e: IOException) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun unFollowUser(followedId: Int): NetworkResponse<BaseResponse> {
        return try {
            val response = userService.unFollowUser(followedId)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }
}