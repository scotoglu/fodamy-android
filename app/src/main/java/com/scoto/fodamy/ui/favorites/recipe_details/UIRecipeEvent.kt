package com.scoto.fodamy.ui.favorites.recipe_details

import androidx.navigation.NavDirections
import com.scoto.fodamy.network.models.Comment

sealed class UIRecipeEvent {
    data class NavigateTo(val directions: NavDirections) : UIRecipeEvent()
    object ShowMessage : UIRecipeEvent() {
        data class ErrorMessage(val message: String) : UIRecipeEvent()
        data class SuccessMessage(val message: String, val isFollowing: Boolean) : UIRecipeEvent()
    }

    object IsFollowing : UIRecipeEvent()
    data class CommentData(val comment: Comment) : UIRecipeEvent()
}
