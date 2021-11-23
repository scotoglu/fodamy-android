package com.scoto.fodamy.network.models.responses

import com.scoto.fodamy.network.models.Pagination

data class RecipeResponse<T>(
    val data: T,
    val pagination: Pagination
)
