package com.scoto.data.local.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scoto.data.utils.RemoteKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.03.2022 at 14:05
 */
@Entity(tableName = "remote_keys_category")
data class RemoteKeysCategory(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val prev: Int?,
    override val next: Int?
) : RemoteKey
