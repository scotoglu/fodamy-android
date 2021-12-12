package com.scoto.fodamy.ui.recipe_details

import androidx.navigation.NavDirections
import com.scoto.fodamy.network.models.Comment

enum class TYPE {
    FOLLOW,
    LIKE
}

sealed class UIRecipeEvent {
    data class NavigateTo(val directions: NavDirections) : UIRecipeEvent()
    data class OpenDialog(val actionId: Int) : UIRecipeEvent()
    data class ShowMessage(val message: String) : UIRecipeEvent()
    object BackTo : UIRecipeEvent()
    data class CommentData(val comment: Comment) : UIRecipeEvent()
}
