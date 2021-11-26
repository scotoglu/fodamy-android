package com.scoto.fodamy.ui.favorites.recipe_details

import androidx.navigation.NavDirections
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.models.Recipe

sealed class UIRecipeEvent {
    data class NavigateTo(val directions: NavDirections) : UIRecipeEvent()
    data class ShowMessage(val message: String) : UIRecipeEvent() {
        data class ErrorMessage(val message: String) : UIRecipeEvent()
        data class SuccessMessage(val message: String) : UIRecipeEvent()
    }

    data class CommentData(val comment: Comment) : UIRecipeEvent()
    data class RecipeData(val recipe: Recipe) : UIRecipeEvent()
}
