package com.scoto.data.network.repositories

import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.AuthService
import com.scoto.domain.models.Auth
import com.scoto.domain.models.Common
import com.scoto.domain.repositories.AuthRepository
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:21
 */
class DefaultAuthRepository @Inject constructor(
    private val authService: AuthService
) : AuthRepository, BaseRepository() {
    override suspend fun register(username: String, email: String, password: String): Auth =
        execute {
            authService.register(username, email, password).toDomainModel()
        }

    override suspend fun forgot(email: String): Auth =
        execute {
            authService.forgot(email).toDomainModel()
        }

    override suspend fun logout(): Common =
        execute {
            authService.logout().toDomainModel()
        }

    override suspend fun login(username: String, password: String): Auth =
        execute {
            authService.login(username, password).toDomainModel()
        }
}