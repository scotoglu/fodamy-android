package com.scoto.data.network.repositories

import com.google.gson.Gson
import com.scoto.data.network.exceptions.Authentication
import com.scoto.data.network.exceptions.BaseException
import com.scoto.data.network.exceptions.GettingEmptyListItem
import com.scoto.data.network.exceptions.SimpleHttpException
import com.scoto.data.network.exceptions.Unauthorized
import com.scoto.domain.models.ErrorControl
import retrofit2.HttpException

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:32
 */
abstract class BaseRepository {
    suspend fun <T : Any> execute(request: suspend () -> T): T {
        try {
            return request.invoke()
        } catch (ex: Exception) {
            throw parseException(ex)
        }
    }

    private fun parseException(ex: Exception): Exception {

        return when (ex) {
            // cast HttpException here.
            is HttpException -> {
                when (ex.response()?.code()) {
                    CODE_UNAUTHORIZIED -> Unauthorized()
                    CODE_AUTHENTICATION -> Authentication()
                    else -> {
                        val response = Gson().fromJson(
                            ex.response()?.errorBody()?.charStream(),
                            ErrorControl::class.java
                        )
                        SimpleHttpException(response.code, response.error)
                    }
                }
            }
            is IndexOutOfBoundsException -> GettingEmptyListItem()
            else -> BaseException(ex.message.toString())
        }
    }

    companion object {
        private const val CODE_AUTHENTICATION = 403
        private const val CODE_UNAUTHORIZIED = 401
    }
}
