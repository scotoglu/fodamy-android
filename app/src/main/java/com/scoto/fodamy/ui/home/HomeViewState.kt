package com.scoto.fodamy.ui.home

sealed class HomeViewState {
    data class IsLogin(val isLogin: Boolean) : HomeViewState()
    data class Success(val message: String) : HomeViewState()
}
