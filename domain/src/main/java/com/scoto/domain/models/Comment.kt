package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:41
 */
@Parcelize
data class Comment(
    val id: Int,
    val text: String,
    val user: User,
    val difference: String
) : Parcelable