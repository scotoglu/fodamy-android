package com.scoto.fodamy.network.api

import com.scoto.fodamy.network.models.responses.AuthResponse
import com.scoto.fodamy.network.models.responses.BaseResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("auth/forgot")
    suspend fun forgot(
        @Field("email") email: String
    ): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(): BaseResponse
}
