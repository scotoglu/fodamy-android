package com.scoto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scoto.data.local.local_dto.UserDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 10:23
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDb: UserDb)

    @Query("select * from user")
    suspend fun getUser(): UserDb

    @Query("delete from user where id=:userId")
    suspend fun deleteUser(userId: Int)
}
