package com.scoto.data.network.repositories

import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.AuthService
import com.scoto.domain.models.Auth
import com.scoto.domain.models.Common
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.utils.DataStoreManager
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:21
 */
class DefaultAuthRepository @Inject constructor(
    private val authService: AuthService,
    private val dataStoreManager: DataStoreManager
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
            val response = authService.logout().toDomainModel()
            dataStoreManager.removeUserId()
            dataStoreManager.removeToken()
            response
        }

    override suspend fun login(username: String, password: String): Auth =
        execute {
            val response = authService.login(username, password).toDomainModel()
            dataStoreManager.saveToken(response.token)
            dataStoreManager.saveUserId(response.user.id)
            response
        }
}