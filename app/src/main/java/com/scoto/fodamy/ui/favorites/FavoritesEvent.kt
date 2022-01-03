package com.scoto.fodamy.ui.favorites

sealed class FavoritesEvent {
    data class IsLogin(val isLogin: Boolean) : FavoritesEvent()
    data class Success(val message: String) : FavoritesEvent()
}
