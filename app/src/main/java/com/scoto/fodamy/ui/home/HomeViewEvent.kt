package com.scoto.fodamy.ui.home

sealed class HomeViewEvent {
    data class IsLogin(val isLogin: Boolean) : HomeViewEvent()
    data class Success(val message: String) : HomeViewEvent()
}
