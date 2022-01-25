package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:37
 */
@Parcelize
data class NumberOfPerson(
    val id: Int,
    val text: String
) : Parcelable
