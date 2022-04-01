package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:39
 */
@Parcelize
data class TimeOfRecipe(
    val id: Int,
    val text: String
) : Parcelable {
    override fun toString(): String {
        return "$text dk"
    }

    companion object {
        val EMPTY = TimeOfRecipe(-1, "")
    }
}
