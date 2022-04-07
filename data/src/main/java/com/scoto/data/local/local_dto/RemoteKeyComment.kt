package com.scoto.data.local.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scoto.data.utils.RemoteKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 25.02.2022 at 13:42
 */
@Entity(tableName = "remote_key_comments")
data class RemoteKeyComment(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val next: Int?,
    override val prev: Int?
) : RemoteKey
