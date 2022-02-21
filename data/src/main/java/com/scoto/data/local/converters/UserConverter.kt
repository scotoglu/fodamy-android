package com.scoto.data.local.converters

import androidx.room.TypeConverter
import com.scoto.data.local.local_dto.UserDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.02.2022 at 13:57
 */
class UserConverter {

    @TypeConverter
    fun userToJson(user: UserDb): String {
        return toJson<UserDb>(user)
    }

    @TypeConverter
    fun jsonToUser(user: String): UserDb {
        return fromJson<UserDb>(user)
    }
}
