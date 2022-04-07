package com.scoto.fodamy.ui.favorites

sealed class FavoritesEvent {
    data class IsLogin(val isLogin: Boolean) : FavoritesEvent()
    object Success : FavoritesEvent()
}
