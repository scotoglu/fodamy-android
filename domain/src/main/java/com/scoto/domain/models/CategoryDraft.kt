package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 16.03.2022
 */
@Parcelize
data class CategoryDraft(
    val id: Int,
    val name: String,
    val mainCategoryId: Int?
) : Parcelable {
    override fun toString(): String {
        return name
    }

    companion object {
        val EMPTY = CategoryDraft(-1, "", null)
    }
}
