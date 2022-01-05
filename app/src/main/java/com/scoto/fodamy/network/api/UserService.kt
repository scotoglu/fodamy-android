package com.scoto.fodamy.network.api

import com.scoto.fodamy.network.models.User
import com.scoto.fodamy.network.models.responses.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("user/{followedId}/following")
    suspend fun followUser(
        @Path("followedId") followedId: Int
    ): BaseResponse<Any>

    @DELETE("user/{followedId}/following")
    suspend fun unFollowUser(
        @Path("followedId") followedId: Int
    ): BaseResponse<Any>

    @GET("user/{userId}/following")
    suspend fun getUserFollowing(
        @Path("userId") userId: Int
    )

    @GET("user/{user_id}")
    suspend fun getUserDetails(
        @Path("user_id") userId: Int
    ): User
}
