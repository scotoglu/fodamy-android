package com.scoto.domain.models

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 10:41
 */
data class RecipePaging(
    val data:List<Recipe>,
    val pagination: Pagination
)
