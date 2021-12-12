package com.scoto.fodamy.ui.favorites

import androidx.navigation.NavDirections

sealed class UIFavoritesEvent {
    data class NavigateTo(val directions: NavDirections) : UIFavoritesEvent()
    data class ShowMessage(val message: String) : UIFavoritesEvent()
}
