package com.scoto.data.local.converters

import androidx.room.TypeConverter

/**
 * @author Sefa ÇOTOĞLU
 * Created 11.02.2022 at 10:43
 */

class BooleanConverter {

    @TypeConverter
    fun booleanToInt(value: Boolean): Int {
        return when (value) {
            false -> 0
            true -> 1
        }
    }

    @TypeConverter
    fun intToBoolean(value: Int): Boolean {
        return when (value) {
            0 -> false
            1 -> true
            else -> false
        }
    }
}
