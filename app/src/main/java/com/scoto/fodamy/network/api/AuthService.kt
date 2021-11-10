package com.scoto.fodamy.network.api

import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.models.AuthResponse
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
    ): Response<AuthResponse>
}