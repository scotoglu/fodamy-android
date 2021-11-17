package com.scoto.fodamy.network.models.responses

import android.os.Parcelable
import com.scoto.fodamy.network.models.Pagination
import com.scoto.fodamy.network.models.Recipe
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeResponse(
    val data: List<Recipe>,
    val pagination: Pagination
) : Parcelable


