package com.scoto.data.network.repositories

import com.scoto.data.network.exceptions.Authentication
import com.scoto.data.network.exceptions.GettingEmptyListItem
import com.scoto.data.network.exceptions.Unauthorized
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
            when (ex) {
                is HttpException -> throw parseException(ex)
                is IndexOutOfBoundsException -> throw GettingEmptyListItem()
                else -> throw ex
            }
        }
    }

    private fun parseException(ex: HttpException): Exception {
        // cast HttpException here.
        return when (ex.response()?.code()) {
            401 -> Unauthorized()
            403 -> Authentication()
            else -> {
                // The other HttpException  will be handled in base viewmodel.
                ex
            }
        }
    }
}
