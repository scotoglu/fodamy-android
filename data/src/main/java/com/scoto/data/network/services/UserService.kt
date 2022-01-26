package com.scoto.data.network.services

import com.scoto.data.network.dto.CommonResponse
import com.scoto.data.network.dto.UserResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:36
 */
interface UserService {

    @POST("user/{followedId}/following")
    suspend fun followUser(
        @Path("followedId") followedId: Int
    ): CommonResponse

    @DELETE("user/{followedId}/following")
    suspend fun unFollowUser(
        @Path("followedId") followedId: Int
    ): CommonResponse

    @GET("user/{userId}/following")
    suspend fun getUserFollowing(
        @Path("userId") userId: Int
    )

    @GET("user/{user_id}")
    suspend fun getUserDetails(
        @Path("user_id") userId: Int
    ): UserResponse
}
