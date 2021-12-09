package com.scoto.fodamy.ui.favorites.category_recipes

import androidx.navigation.NavDirections

sealed class UICategoryEvent {
    data class NavigateTo(val directions: NavDirections) : UICategoryEvent()
    data class ShowMessage(val message: String) : UICategoryEvent()
    object BackTo : UICategoryEvent()
    data class OpenDialog(val actionId: Int) : UICategoryEvent()
}
