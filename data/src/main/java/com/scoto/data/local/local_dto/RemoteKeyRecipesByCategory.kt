package com.scoto.data.local.local_dto

import androidx.room.Entity
import com.scoto.data.utils.RemoteKey

/**
 * @author Sefa ÇOTOĞLU
 * Created 25.02.2022 at 11:37
 */
@Entity(tableName = "remote_key_category_recipes")
data class RemoteKeyRecipesByCategory(
    override val id: Int,
    override val next: Int?,
    override val prev: Int?
) : RemoteKey
