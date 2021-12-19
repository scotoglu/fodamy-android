package com.scoto.fodamy.ext

import com.google.gson.Gson
import com.scoto.fodamy.network.models.responses.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

fun Exception.handleException(): String {
    return when (this) {
        is HttpException -> {
            val message = Gson().fromJson(
                this.response()?.errorBody()?.charStream(),
                ErrorResponse::class.java
            )
            message.error
        }
        is IOException -> {
            "İnternet bağlantınızı kontrol edin"
        }
        else -> {
            this.message.toString()
        }
    }
}
