package com.scoto.fodamy.ui.favorites

import androidx.navigation.NavDirections

sealed class UIFavoritesEvent {
    data class NavigateTo(val directions: NavDirections) : UIFavoritesEvent()
    data class IsLogin(val isLogin: Boolean) : UIFavoritesEvent()
    object ShowMessage {
        data class Success(val message: String) : UIFavoritesEvent()
        data class Error(val message: String) : UIFavoritesEvent()
    }
}
