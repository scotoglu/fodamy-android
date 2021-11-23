package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.api.UserService
import com.scoto.fodamy.network.models.responses.BaseResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


interface UserRepository {
    suspend fun followUser(followedId: Int): NetworkResult<BaseResponse>

    suspend fun unFollowUser(followedId: Int): NetworkResult<BaseResponse>
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {
    override suspend fun followUser(followedId: Int): NetworkResult<BaseResponse> {
        return try {
            val response = userService.followUser(followedId)
            NetworkResult.Success(response)
        } catch (e: IOException) {
            NetworkResult.Error.IOError(e.message)
        } catch (e: HttpException) {
            NetworkResult.Error.HttpError(e.message)
        }
    }

    override suspend fun unFollowUser(followedId: Int): NetworkResult<BaseResponse> {
        TODO("Not yet implemented")
    }
}