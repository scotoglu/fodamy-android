package com.scoto.fodamy.ui.favorites

sealed class FavoritesViewState {
    data class IsLogin(val isLogin: Boolean) : FavoritesViewState()
    data class Success(val message: String) : FavoritesViewState()
}
