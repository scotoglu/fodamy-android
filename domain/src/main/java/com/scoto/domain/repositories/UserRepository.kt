package com.scoto.domain.repositories

import com.scoto.domain.models.Common
import com.scoto.domain.models.User

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:22
 */
interface UserRepository {
    suspend fun followUser(followedId: Int): Common
    suspend fun unFollowUser(followedId: Int): Common
    suspend fun getUserDetails(userId: Int): User
}
