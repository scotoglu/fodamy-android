package com.scoto.data.local.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scoto.data.utils.RemoteKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 23.02.2022 at 10:19
 */
@Entity(tableName = "remote_keys_editor")
data class RemoteKeysEditor(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val prev: Int?,
    override val next: Int?
) : RemoteKey
