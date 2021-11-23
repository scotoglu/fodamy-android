package com.scoto.fodamy.network.api

import com.scoto.fodamy.network.models.responses.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("user/{followedId}/following")
    suspend fun followUser(
        @Path("followedId") followedId: Int
    ): BaseResponse

    @DELETE("user/{followedId}/following")
    suspend fun unFollowUser(
        @Path("followedId") followedId: Int
    ): BaseResponse

}