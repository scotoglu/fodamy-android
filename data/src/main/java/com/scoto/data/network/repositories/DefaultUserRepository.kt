package com.scoto.data.network.repositories

import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.UserService
import com.scoto.domain.models.Common
import com.scoto.domain.models.User
import com.scoto.domain.repositories.UserRepository
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:35
 */
class DefaultUserRepository @Inject constructor(
    private val userService: UserService
) : UserRepository, BaseRepository() {

    override suspend fun followUser(followedId: Int): Common =
        execute {
            userService.followUser(followedId).toDomainModel()
        }

    override suspend fun unFollowUser(followedId: Int): Common =
        execute {
            userService.unFollowUser(followedId).toDomainModel()
        }

    override suspend fun getUserDetails(userId: Int): User =
        execute {
            userService.getUserDetails(userId).toDomainModel()
        }
}
