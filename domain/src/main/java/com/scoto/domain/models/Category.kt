package com.scoto.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:34
 */
@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val image: Image?,
    val recipes: List<Recipe>?
) : Parcelable
