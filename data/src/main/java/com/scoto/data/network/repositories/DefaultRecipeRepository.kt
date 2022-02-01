package com.scoto.data.network.repositories

import com.scoto.data.mapper.toDomainModel
import com.scoto.data.network.services.RecipeService
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:41
 */
class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository, BaseRepository() {

    override suspend fun getEditorChoiceRecipes(page: Int): List<Recipe> =
        execute {
            recipeService.getEditorChoiceRecipes(page).data.map {
                it.toDomainModel()
            }
        }

    override suspend fun getLastAdded(page: Int): List<Recipe> =
        execute {
            recipeService.getLastAddedRecipes(page).data.map { it.toDomainModel() }
        }

    override suspend fun getRecipeById(recipeId: Int): Recipe =
        execute {
            recipeService.getRecipeById(recipeId).toDomainModel()
        }

    override suspend fun getRecipeComments(recipeId: Int, page: Int): List<Comment> =
        execute {
            recipeService.getRecipeComments(recipeId, page).data.map { it.toDomainModel() }
        }

    override suspend fun getFirstComment(recipeId: Int): Comment =
        execute {
            recipeService.getRecipeComments(recipeId, 1).data.get(0).toDomainModel()
        }

    override suspend fun sendComment(recipeId: Int, text: String): Unit =
        execute {
            recipeService.sendComment(recipeId, text).toDomainModel()
        }

    override suspend fun editComment(recipeId: Int, commentId: Int, text: String): Unit =
        execute {
            recipeService.editComment(recipeId, commentId, text).toDomainModel()
        }

    override suspend fun deleteComment(recipeId: Int, commentId: Int): Unit =
        execute {
            recipeService.deleteComment(recipeId, commentId).toDomainModel()
        }

    override suspend fun likeRecipe(recipeId: Int): Unit =
        execute {
            recipeService.likeRecipe(recipeId)
        }

    override suspend fun dislikeRecipe(recipeId: Int): Unit =
        execute {
            recipeService.dislikeRecipe(recipeId)
        }

    override suspend fun getCategoriesWithRecipes(page: Int): List<Category> =
        execute {
            recipeService.getCategoriesWithRecipes(page).data
                .map { it.toDomainModel() }
                .filter { it.recipes?.size!! > 0 }
        }

    override suspend fun getRecipesByCategory(categoryId: Int, page: Int): List<Recipe> =
        execute {
            recipeService.getRecipesByCategory(categoryId, page).data.map { it.toDomainModel() }
        }
}
