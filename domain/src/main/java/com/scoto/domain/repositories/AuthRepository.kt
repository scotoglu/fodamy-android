package com.scoto.domain.repositories

import com.scoto.domain.models.Auth
import com.scoto.domain.models.Common

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:26
 */
interface AuthRepository {
    suspend fun register(username: String, email: String, password: String): Auth
    suspend fun forgot(email: String): Auth
    suspend fun logout(): Common
    suspend fun login(username: String, password: String): Auth
}
