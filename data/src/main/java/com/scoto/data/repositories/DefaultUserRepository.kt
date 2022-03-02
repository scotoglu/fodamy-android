package com.scoto.data.repositories

import com.scoto.data.local.dao.UserDao
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.remote.services.UserService
import com.scoto.domain.models.User
import com.scoto.domain.repositories.UserRepository
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:35
 */
class DefaultUserRepository @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository, BaseRepository() {

    override suspend fun followUser(followedId: Int): Unit =
        execute {
            userService.followUser(followedId)
        }

    override suspend fun unFollowUser(followedId: Int): Unit =
        execute {
            userService.unFollowUser(followedId)
        }

    override suspend fun getUserDetails(userId: Int): User =
        execute {
            userService.getUserDetails(userId).toDomainModel()
        }
}
