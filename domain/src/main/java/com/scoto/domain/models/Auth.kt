package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:43
 */
@Parcelize
data class Auth(
    val token: String,
    val user: User
) : Parcelable
