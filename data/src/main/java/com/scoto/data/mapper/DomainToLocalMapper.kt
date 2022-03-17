package com.scoto.data.mapper

import com.scoto.data.local.local_dto.CategoryDraftDb
import com.scoto.data.local.local_dto.NumberOfPersonDb
import com.scoto.data.local.local_dto.RecipeDraftDb
import com.scoto.data.local.local_dto.TimeOfRecipeDb
import com.scoto.domain.models.CategoryDraft
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.models.TimeOfRecipe

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */

fun RecipeDraft.toLocalDto(): RecipeDraftDb {
    return RecipeDraftDb(
        id = this.id,
        title = this.title,
        direction = this.direction,
        ingredients = this.ingredients,
        category = this.category.toLocalDto(),
        numberOfPerson = this.numberOfPerson.toLocalDto(),
        timeOfRecipe = this.timeOfRecipe.toLocalDto(),
        images = this.image
    )
}

fun NumberOfPerson.toLocalDto(): NumberOfPersonDb {
    return NumberOfPersonDb(id, text)
}

fun TimeOfRecipe.toLocalDto(): TimeOfRecipeDb {
    return TimeOfRecipeDb(id, text)
}

fun CategoryDraft.toLocalDto(): CategoryDraftDb {
    return CategoryDraftDb(
        id = this.id,
        name = this.name,
        mainCategoryId = this.mainCategoryId
    )
}
