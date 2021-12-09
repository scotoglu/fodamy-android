package com.scoto.fodamy.ui.favorites.main

import androidx.navigation.NavDirections

sealed class UIFavoritesEvent {
    data class NavigateTo(val directions: NavDirections) : UIFavoritesEvent()
    data class ShowMessage(val message: String) : UIFavoritesEvent()
}
