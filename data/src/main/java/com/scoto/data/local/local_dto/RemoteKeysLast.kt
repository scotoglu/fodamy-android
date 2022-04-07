package com.scoto.data.local.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scoto.data.utils.RemoteKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 24.02.2022 at 17:27
 */
@Entity(tableName = "remote_keys_last")
data class RemoteKeysLast(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val prev: Int?,
    override val next: Int?
) : RemoteKey
