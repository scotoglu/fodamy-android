package com.scoto.domain.usecase

import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.repositories.UserRepository
import com.scoto.domain.usecase.base.UseCase
import com.scoto.domain.usecase.params.FollowParams
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 31.01.2022 at 17:02
 */
class UnfollowUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository
) : UseCase<FollowParams, Recipe>() {
    override suspend fun invoke(params: FollowParams): Recipe {
        userRepository.unFollowUser(params.userId)
        return recipeRepository.getRecipeById(params.recipeId)
    }
}