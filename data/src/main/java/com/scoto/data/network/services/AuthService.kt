package com.scoto.data.network.services

import com.scoto.data.network.dto.AuthResponse
import com.scoto.data.network.dto.CommonResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:36
 */
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
    ): AuthResponse

    @POST("auth/logout")
    suspend fun logout(): CommonResponse
}
