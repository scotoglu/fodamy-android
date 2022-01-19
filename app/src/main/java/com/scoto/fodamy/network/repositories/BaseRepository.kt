package com.scoto.fodamy.network.repositories

import com.scoto.fodamy.helper.states.NetworkResponse

/**
 * @author Sefa ÇOTOĞLU
 * Created 9.01.2022 at 20:29
 */

interface BaseRepository {
    suspend fun <T : Any> execute(request: suspend () -> T): NetworkResponse<T>
}

open class BaseRepositoryImpl : BaseRepository {
    override suspend fun <T : Any> execute(request: suspend () -> T): NetworkResponse<T> {
        return try {
            NetworkResponse.Success(request.invoke())
        } catch (ex: Exception) {
            NetworkResponse.Error(ex)
        }
    }
}

abstract class Base {
    suspend fun <T : Any> makeCall(request: suspend () -> T): T {
        return request.invoke()
    }
}