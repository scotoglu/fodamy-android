package com.scoto.domain.usecase

import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.usecase.base.UseCase
import com.scoto.domain.usecase.params.RecipeParams
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 31.01.2022 at 12:33
 */
class LikeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : UseCase<RecipeParams, Recipe>() {
    override suspend fun invoke(params: RecipeParams): Recipe {
        recipeRepository.likeRecipe(params.recipeId)
        return recipeRepository.getRecipeById(params.recipeId, params.onlyRemote)
    }
}
