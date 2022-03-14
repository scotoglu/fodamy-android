package com.scoto.data.mapper

import com.scoto.data.local.local_dto.RecipeDraftDb
import com.scoto.domain.models.RecipeDraft

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */

fun RecipeDraft.toLocalDto(): RecipeDraftDb {
    return RecipeDraftDb(
        title = this.title,
        direction = this.direction,
        ingredients = this.ingredients,
        categoryId = this.categoryId,
        numberOfPersonId = this.numberOfPersonId,
        timeOfRecipeId = this.timeOfRecipeId,
        images = this.image
    )
}
