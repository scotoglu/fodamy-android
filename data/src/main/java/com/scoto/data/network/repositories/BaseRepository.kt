package com.scoto.data.network.repositories

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:32
 */
abstract class BaseRepository {
    suspend fun <T : Any> execute(request:suspend () -> T): T {
        return request.invoke()
    }
}